package com.funkyfunctor.internal.instrumentationDemo.javaAgent

import com.funkyfunctor.internal.instrumentationDemo.javaAgent.MyJavaAgent.printMessage
import javassist.{ClassPool, CtClass}

import java.io.ByteArrayInputStream
import scala.util.Try

object InstrumentationMethods {
  private def getClass(content: Array[Byte], className: String): CtClass = {
    val is = new ByteArrayInputStream(content)
    val result = ClassPool.getDefault.makeClass(is)

    val ln = System.lineSeparator()

    val msg = result
      .getDeclaredMethods
      .map { meth => s">>> - ${meth.getName}" }
      .mkString(s"List of methods for $className:$ln", ln, "")

    printMessage(msg)

    result
  }

  private def instrumentMethods(clazz: CtClass, methods: Seq[String]): Array[Byte] = {
    methods.foreach { methodName =>
      Try {
        val method = clazz.getDeclaredMethod(methodName)

        //method.insertBefore("{ System.err.print(\">>> Arguments for the method -> \"); System.err.println($args); }")
        method.insertBefore("{ com.funkyfunctor.internal.instrumentationDemo.javaAgent.InstrumentationMethods.printArguments(\"" + methodName + "\", $args);  }")

        //method.insertAfter("{ System.err.print(\">>> Return value  -> \"); System.err.println($_); }")
        method.insertAfter("{ com.funkyfunctor.internal.instrumentationDemo.javaAgent.InstrumentationMethods.printReturnValue(\"" + methodName + "\", $_); }")
      }
    }

    clazz.toBytecode
  }

  def instrumentMethods(clazz: Array[Byte], className: String, methods: Seq[String]): Array[Byte] = {
    val c = getClass(clazz, className)
    instrumentMethods(c, methods)
  }

  def printArguments(methodName: String, args: Array[Any]): Unit = {
    val msg = args.map(arg =>
      s"$arg (${arg.getClass.getCanonicalName})"
    ).mkString(s"List of arguments for '$methodName' [ ", " ; ", "]")

    printMessage(msg)
  }

  def printReturnValue(methodName: String, obj: Any): Unit = {
    val msg = s"""Return value for '$methodName' -> $obj (${obj.getClass.getCanonicalName})"""

    printMessage(msg)
  }
}
