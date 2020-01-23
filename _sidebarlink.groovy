#!/usr/bin/env groovy
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import java.net.URLConnection
def call(String buildStatus = 'STARTED') {
    if (GIT_URL.contains("https://")) {
        repos = ("${GIT_URL}" =~ /https:\/\/gitlab.volansys.com\/(.+)\.git/)[ 0 ][ 1 ] 
    } else {
        repos = ("${GIT_URL}" =~ /.*:(.+)\.git/)[ 0 ][ 1 ] 
    }
    
    glcommiturl = "${repos}/commit/${GIT_COMMIT}"
    addBadge(icon: "folder.gif", text: "GitLab Commit", link: "${glcommiturl}")
    //addShortText background: 'cyan', borderColor: '', color: 'blue', link: '${glcommiturl}', text: ''
}
