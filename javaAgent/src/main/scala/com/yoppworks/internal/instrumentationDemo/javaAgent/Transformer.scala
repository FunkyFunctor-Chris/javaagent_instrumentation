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
      System.err.println(s">>> Class '$className' loaded")

    instrumentationRules.get(className) match {
      case Some(methods) => InstrumentationMethods.instrumentMethods(classfileBuffer, className, methods)
      case None => classfileBuffer
    }
  }
}
