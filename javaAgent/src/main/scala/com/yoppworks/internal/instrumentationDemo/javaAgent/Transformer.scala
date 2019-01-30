package com.yoppworks.internal.instrumentationDemo.javaAgent

import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain

class Transformer() extends ClassFileTransformer {
  val instrumentationRules = Map(
    "com/yoppworks/internal/instrumentationDemo/baseApp/actors/ScheduledActor" -> Seq("getCurrentTime")
  )

  override def transform(loader: ClassLoader,
                         className: String,
                         classBeingRedefined: Class[_],
                         protectionDomain: ProtectionDomain,
                         classfileBuffer: Array[Byte]): Array[Byte] = {
    if (className.startsWith("com/yoppworks"))
      Option(className) match {
        case Some(name) =>
          System.err.println(s">>> Class '$name' loaded")
        case None =>
          System.err.println(s">>> Anonymous class loaded from loader '$loader'")
      }

    if (instrumentationRules.keySet.contains(className))
      InstrumentationMethods.instrumentMethods(classfileBuffer, className, instrumentationRules(className))
    else
      classfileBuffer
  }
}
