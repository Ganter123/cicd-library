#!/usr/bin/env groovy

def call() {
    env.BUILD_USER = null
    wrap([$class: 'BuildUser']) {
        println "GET_BUILD_USER is ${BUILD_USER}"
        env.BUILD_USER = "${BUILD_USER}"
    }
}