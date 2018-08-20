package ru.dev.gbixahue.library.tools.network

import java.net.URL

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
open class NetworkConfig(protocol: String, host: String, version: String) {

	private val SLASH = "/"

	val url = URL(protocol, host, if (version.isNotEmpty()) version.plus(SLASH) else version)
}