package ru.dev.gbixahue.library.arch.routing

import ru.dev.gbixahue.library.arch.routing.cicerone.Router

/**
 * Created by Anton Zhilenkov 12.01.18
 */
interface RouterProvider {
	fun getRootRouter(): Router?
	fun getBaseRouter(): Router?
}
