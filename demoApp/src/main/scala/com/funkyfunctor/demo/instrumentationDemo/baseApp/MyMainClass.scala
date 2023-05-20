package com.funkyfunctor.demo.instrumentationDemo.baseApp

import zio._
import zio.http._

object MyMainClass extends ZIOAppDefault {

  val app: App[Any] =
    Http.collect[Request] { Routes.helloWorldRoute.orElse(Routes.pingPongRoute).orElse(Routes.plusOne) }

  override val run =
    Server.serve(app).provide(Server.default)
}
