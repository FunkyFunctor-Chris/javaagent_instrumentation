package com.yoppworks.internal.instrumentationDemo.javaAgent

import java.io.ByteArrayInputStream

import javassist.{ClassPool, CtClass, CtMethod}

import scala.util.Try

object InstrumentationMethods {
  private def getClass(content: Array[Byte], className: String): CtClass = {
    val is = new ByteArrayInputStream(content)
    val result = ClassPool.getDefault.makeClass(is)

    val ln = System.lineSeparator()

    val msg = result
      .getDeclaredMethods
      .map { meth => s">>> - ${meth.getName}" }
      .mkString(s">>> List of methods for $className:$ln", ln, "")

    System.err.println(msg)

    result
  }

  private def instrumentMethods(clazz: CtClass, methods: Seq[String]): Array[Byte] = {
    methods.foreach { methodName =>
      Try {
        val method = clazz.getDeclaredMethod(methodName)

        //method.insertBefore("{ System.err.print(\">>> Arguments for the method -> \"); System.err.println($args); }")
        method.insertBefore("{ com.yoppworks.internal.instrumentationDemo.javaAgent.InstrumentationMethods.printArguments($args);  }")

        //method.insertAfter("{ System.err.print(\">>> Return value  -> \"); System.err.println($_); }")
        method.insertAfter("{ com.yoppworks.internal.instrumentationDemo.javaAgent.InstrumentationMethods.printReturnValue($_); }")
      }
    }

    clazz.toBytecode
  }

  def instrumentMethods(clazz: Array[Byte], className: String, methods: Seq[String]): Array[Byte] = {
    val c = getClass(clazz, className)
    instrumentMethods(c, methods)
  }

  def printArguments(args: Array[Any]): Unit = {
    val msg = args.map(arg =>
      s"$arg (${arg.getClass.getCanonicalName})"
    ).mkString(s">>> List of arguments [ ", " ; ", "]")

    System.err.println(msg)
  }

  def printReturnValue(obj: Any): Unit = {
    val msg = s""">>> Return value  -> $obj (${obj.getClass.getCanonicalName})"""

    System.err.println(msg)
  }
}
