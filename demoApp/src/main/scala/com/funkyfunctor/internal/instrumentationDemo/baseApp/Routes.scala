package com.funkyfunctor.internal.instrumentationDemo.baseApp

import zio.http._

import scala.util.{Failure, Success, Try}

object Routes {
  val helloWorldRoute: PartialFunction[Request, Response] = {
    case Method.GET -> !! / "hello"        => Response.text("Hello World!")
    case Method.GET -> !! / "hello" / name => Response.text(s"Hello $name")
  }

  val pingPongRoute: PartialFunction[Request, Response] = {
    case Method.GET -> !! / "ping" => Response.text("pong")
    case Method.GET -> !! / "pong" => Response.text("ping")
  }

  val plusOne: PartialFunction[Request, Response] = { case Method.GET -> !! / number => plusOneMethod(number) }

  private def plusOneMethod(str: String) = {
    Try {
      str.toInt + 1
    } match {
      case Failure(_) =>
        Response.apply(
          status = Status.InternalServerError,
          body = Body.fromCharSequence(s"Unable to add 1 to '$str'")
        )
      case Success(value) => Response.text(value.toString)
    }
  }
}
