package me.schnavid.kaninchenbau.dav

import com.gitlab.mvysny.konsumexml.Konsumer

sealed class MultistatusResponse(
    val href: HRef,
    val error: Error?,
    val responseDescription: ResponseDescription?,
    val location: Location?,
) {
    companion object : XmlConsumer<MultistatusResponse> {
        override fun xml(k: Konsumer): MultistatusResponse {
            k.checkCurrent("response")

            val href = k.child("href") { HRef.xml(this) }

            val firstPropStat = k.childOrNull("propstat") { Propstat.xml(this) }

            return if (firstPropStat != null) {
                val propstats = mutableListOf(firstPropStat)

                var propstatsFinished = false
                while (!propstatsFinished) {
                    val c = k.childOrNull("propstat") { Propstat.xml(this) }

                    if (c == null) {
                        propstatsFinished = true
                    } else {
                        propstats.add(c)
                    }
                }

                val error = null // TODO: k.childOrNull("error") { Error.xml(this) }

                val responseDescription = k.childOrNull("responsedescription") { ResponseDescription.xml(this) }

                val location = k.childOrNull("location") { Location.xml(this) }

                Propstats(href, propstats, error, responseDescription, location)
            } else {
                val hrefs = mutableListOf<HRef>()

                var hrefsFinished = false
                while (!hrefsFinished) {
                    val c = k.childOrNull("href") { HRef.xml(this) }

                    if (c == null) {
                        hrefsFinished = true
                    } else {
                        hrefs.add(c)
                    }
                }

                val status = k.child("status") { Status.xml(this) }

                val error = null // TODO: k.childOrNull("error") { Error.xml(this) }

                val responseDescription = k.childOrNull("responsedescription") { ResponseDescription.xml(this) }

                val location = k.childOrNull("location") { Location.xml(this) }

                HrefsStatus(href, hrefs, status, error, responseDescription, location)
            }
        }
    }

    class HrefsStatus(
        href: HRef,
        val hrefs: List<HRef>,
        val status: Status,
        error: Error?,
        responseDescription: ResponseDescription?,
        location: Location?,
    ) : MultistatusResponse(
        href,
        error, responseDescription, location
    )

    class Propstats(
        href: HRef,
        val propstats: List<Propstat>,
        error: Error?,
        responseDescription: ResponseDescription?,
        location: Location?,
    ) : MultistatusResponse(
        href,
        error, responseDescription, location
    )
}