import java.lang.*
import java.util.*
import java.io.*
import java.net.*
import groovy.lang.*
import groovy.util.*
import groovy.json.*

/*
* This function is used to get the Stage and Step details using Jenkins API.
* It further Pass the output it to the stagedetailtemplate() for the Template generation for
* email and slack notifications
*/
def call() {

    def stageArr = []

    //Initiating the API Call using HTTP Request and Parsing it with the Json Slurper Libraries
    def apiUrl = BUILD_URL + "wfapi/describe"
    def response = httpdrequestfunc(apiUrl)
    responseJson = jsonParse(response.content)
    stages = responseJson.get("stages")

    // Iterating The Stages present in the Job and getting the href for API describing Failed stage
    for(stage in stages) {

        def stageName = stage.name
        def stageStatus = stage.status
        def stageHref = stage._links.self.href
        def errorMessage = ""
        def errorType = ""
        def consoleLink = ""

        if (stageStatus == "FAILED") {
            errorMessage = responseJson.stages.error.message.unique()[0]
            errorType = responseJson.stages.error.type.unique()[0]

            if ((stageHref != null) && (stageHref.size() > 0)) {
                failedApiLink = JENKINS_URL + stageHref
                def failureResponse = httpdrequestfunc(failedApiLink)
                failedstageFlowNodes = jsonParse(failureResponse.content)
                failedStepStatus = failedstageFlowNodes.stageFlowNodes.status.unique()

                if (failedstageFlowNodes != null && failedstageFlowNodes.size() > 0) {

                	if(failedStepStatus != null && failedStepStatus.size() > 0){
	                	failedStepStatus = failedStepStatus[failedStepStatus.size()-1]

	                	if(failedStepStatus == "FAILED"){
							consoleLinks = failedstageFlowNodes.stageFlowNodes._links.console.href
							consoleLinks = consoleLinks[consoleLinks.size()-1]
	                        consoleLink = JENKINS_URL + consoleLinks
	                	}
	            	}
	        	}
            }
        }

        def info = [
            'stageName': "${stageName}",
            'stageStatus': "${stageStatus}",
            'errorMessage': "${errorType}: ${errorMessage}",
            'consoleLink': "${consoleLink}"
        ]
        stageDetails = groovy.json.JsonOutput.toJson(info)
        stageArr = stageArr + stageDetails
    }   
	    return stageArr
}

@NonCPS
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}

def httpdrequestfunc(apiUrl) {
    httpRequest authentication: 'JENKINS_API_ACCESS_TOKEN', acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', httpMode: 'POST', url: "${apiUrl}"
}