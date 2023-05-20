package com.funkyfunctor.internal.instrumentationDemo.javaAgent

import java.lang.instrument.{ClassFileTransformer, Instrumentation}
import java.security.ProtectionDomain
import MyJavaAgent.printMessage

class Transformer() extends ClassFileTransformer {
  val instrumentationRules = Map(
    "com/funkyfunctor/demo/instrumentationDemo/baseApp/Routes$" -> Seq("com$funkyfunctor$demo$instrumentationDemo$baseApp$Routes$$plusOneMethod")
  )

  override def transform(loader: ClassLoader,
                         className: String,
                         classBeingRedefined: Class[_],
                         protectionDomain: ProtectionDomain,
                         classfileBuffer: Array[Byte]): Array[Byte] = {
    if (className.startsWith("com/funkyfunctor/demo"))
      printMessage(s"Class '$className' loaded")

    instrumentationRules.get(className) match {
      case Some(methods) => InstrumentationMethods.instrumentMethods(classfileBuffer, className, methods)
      case None => classfileBuffer
    }
  }
}
