package ru.dev.gbixahue.library.arch.routing

import ru.dev.gbixahue.library.arch.routing.cicerone.Router

/**
 * Created by Anton Zhilenkov on 17.07.18.
 */
interface RouterHolder {
	fun attachRouter(router: Router)
}