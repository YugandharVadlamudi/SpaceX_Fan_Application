package com.example.spacexfanapplication.ui.home.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LaunchDetailsResponse(
    @SerializedName("auto_update")
        val autoUpdate: Boolean?=null,
    @SerializedName("capsules")
        val capsules: List<Any?>?=null,
    @SerializedName("cores")
        val cores: List<Core?>?=null,
    @SerializedName("crew")
        val crew: List<Any?>?=null,
    @SerializedName("date_local")
        val dateLocal: String?=null,
    @SerializedName("date_precision")
        val datePrecision: String?=null,
    @SerializedName("date_unix")
        val dateUnix: Int?=null,
    @SerializedName("date_utc")
        val dateUtc: String?=null,
    @SerializedName("details")
        val details: String?=null,
    @SerializedName("failures")
        val failures: List<Any?>?=null,
    @SerializedName("fairings")
        val fairings: Fairings?=null,
    @SerializedName("flight_number")
        val flightNumber: Int?=null,
    @SerializedName("id")
        val id: String?=null,
    @SerializedName("launch_library_id")
        val launchLibraryId: String?=null,
    @SerializedName("launchpad")
        val launchpad: String?=null,
    @SerializedName("links")
        val links: Links?=null,
    @SerializedName("name")
        val name: String?=null,
    @SerializedName("net")
        val net: Boolean?=null,
    @SerializedName("payloads")
        val payloads: List<String?>?=null,
    @SerializedName("rocket")
        val rocket: String?=null,
    @SerializedName("ships")
        val ships: List<Any?>?=null,
    @SerializedName("static_fire_date_unix")
        val staticFireDateUnix: Int?=null,
    @SerializedName("static_fire_date_utc")
        val staticFireDateUtc: String?=null,
    @SerializedName("success")
        val success: Boolean?=null,
    @SerializedName("tbd")
        val tbd: Boolean?=null,
    @SerializedName("upcoming")
        val upcoming: Boolean?=null,
    @SerializedName("window")
        val window: Int?=null,
    @SerializedName("is_fav")
        var isFav: Boolean=false
    ):Serializable {
        data class Core(
            @SerializedName("core")
            val core: Any?=null,
            @SerializedName("flight")
            val flight: Any?=null,
            @SerializedName("gridfins")
            val gridfins: Boolean?=null,
            @SerializedName("landing_attempt")
            val landingAttempt: Boolean?=null,
            @SerializedName("landing_success")
            val landingSuccess: Any?=null,
            @SerializedName("landing_type")
            val landingType: String?=null,
            @SerializedName("landpad")
            val landpad: Any?=null,
            @SerializedName("legs")
            val legs: Boolean?=null,
            @SerializedName("reused")
            val reused: Boolean?=null
        ):Serializable

        data class Fairings(
            @SerializedName("recovered")
            val recovered: Any?=null,
            @SerializedName("recovery_attempt")
            val recoveryAttempt: Any?=null,
            @SerializedName("reused")
            val reused: Any?=null,
            @SerializedName("ships")
            val ships: List<Any?>?=null
        ):Serializable

        data class Links(
            @SerializedName("article")
            val article: Any?=null,
            @SerializedName("flickr")
            val flickr: Flickr?=null,
            @SerializedName("patch")
            val patch: Patch?=null,
            @SerializedName("presskit")
            val presskit: Any?=null,
            @SerializedName("reddit")
            val reddit: Reddit?=null,
            @SerializedName("webcast")
            val webcast: Any?=null,
            @SerializedName("wikipedia")
            val wikipedia: String?=null,
            @SerializedName("youtube_id")
            val youtubeId: Any?=null
        ):Serializable {
            data class Flickr(
                @SerializedName("original")
                val original: List<Any?>?=null,
                @SerializedName("small")
                val small: List<Any?>?=null
            ):Serializable
    
            data class Patch(
                @SerializedName("large")
                val large: String?=null,
                @SerializedName("small")
                val small: String?=null
            ):Serializable

            data class Reddit(
                @SerializedName("campaign")
                val campaign: String?=null,
                @SerializedName("launch")
                val launch: Any?=null,
                @SerializedName("media")
                val media: Any?=null,
                @SerializedName("recovery")
                val recovery: String?=null
            ):Serializable
        }
    }
