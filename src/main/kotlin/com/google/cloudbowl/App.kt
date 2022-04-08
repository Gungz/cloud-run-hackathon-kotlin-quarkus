package com.google.cloudbowl

import java.util.Random
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/{s:.*}")
class App {
    data class Self(val href: String)

    data class Links(val self: Self)

    data class PlayerState(val x: Int, val y: Int, val direction: String, val wasHit: Boolean, val score: Int)

    data class Arena(val dims: List<Int>, val state: Map<String, PlayerState>)

    data class ArenaUpdate(val _links: Links, val arena: Arena)

    @GET
    fun index(): String {
        return "Let the battle begin!"
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    fun index(arenaUpdate: ArenaUpdate?): String {
        println(arenaUpdate)
        val myState = arenaUpdate?.arena?.state?.get(arenaUpdate?._links?.self?.href)
        val commands = arrayOf("F", "R", "L", "T")
        for((k, v) in arenaUpdate?.arena?.state.orEmpty()) {
            if (v.x == myState.x) {
                val distance = v.y - myState.y
                if (distance <= 3 && distance > 0 && myState.direction == "S") {
                    return commands[3]
                } else if (distance >= -3 && distance < 0 && myState.direction == "N") {
                    return commands[3]
                }
            } else if (v.y == myState.y) {
                val distance = v.x - myState.x
                if (distance <= 3 && distance > 0 && myState.direction == "E") {
                    return commands[3]
                } else if (distance >= -3 && distance < 0 && myState.direction == "W") {
                    return commands[3]
                }
            }
        }
        val i = Random().nextInt(4)
        return commands[i]
    }
}
