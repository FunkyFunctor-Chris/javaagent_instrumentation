package com.funkyfunctor.internal.instrumentationDemo.javaAgent

case class ClassRule(className: String,
                     method: Map[String, MethodRule]) {
  def toTuple: (String, ClassRule) = className -> this
}

case class MethodRule(methodName: String,
                      arguments: Seq[Arguments] = Nil,
                      captureReturnValue: Boolean = false) {
  def toTuple: (String, MethodRule) = methodName -> this
}

case class Arguments(argType: String, capture: Boolean = false)