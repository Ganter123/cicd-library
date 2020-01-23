#!/usr/bin/env groovy

/*
* creates a slack and email template for details
*/

import java.lang.*
import java.util.*
import java.io.*
import java.net.*
import groovy.lang.*
import groovy.util.*
import groovy.json.*

def call(stages) {

    def i = 1;
    def color = ""
    def slackemoji = ["FAILED":":heavy_multiplication_x:","SUCCESS":":heavy_check_mark:","NOT_EXECUTED":":heavy_minus_sign:"]
    def slackMessage = ""
    def emailTemplate = """
    
<table style="border: 3px solid #d1aa67;  
            width: 100%;
            -webkit-border-radius: 10px;
            -moz-border-radius: 10px;
            border-radius: 10px;
            border-spacing: 0px;
            background: white" spacing=0 cellpadding="0" cellspacing="0">
	<tr>
		<th style="border-bottom: 2px solid #DFDFDF;
          	color:#ffffff;
            text-align: center;
            background: #d1aa67;
            padding: 5px;">Step</th>
		<th style="border-bottom: 2px solid #DFDFDF;
          	color:#ffffff;
            text-align: center;
            background: #d1aa67;
            padding: 5px;">Status</th>
		<th style="border-bottom: 2px solid #DFDFDF;
          	color:#ffffff;
            text-align: center;
            background: #d1aa67;
            padding: 5px;">Error</th>
	</tr>
    """

    // Seperated out Json array in a variable stagInfoArray from the json object   
    

    for(obj in stages) {
    	def stage = jsonParse(obj)
        if(stage.stageStatus == "IN_PROGRESS") break;
        status = stage.stageStatus
        def status_color
        name = stage.stageName
        
        if(status == "SUCCESS") {
        	emailTemplate = emailTemplate + """
            <tr>
                <td style="border-top: 1px solid #DFDFDF; color:#070e25; padding: 5px;">${name}</td>
                <td style="border-top: 1px solid #DFDFDF; color:#ffffff; padding: 5px; text-align: center; font-color:#155724; background: green;" class="center-align ${status}">${status}</td>
        	"""
    	}
    	if(status == "FAILED") {
        	emailTemplate = emailTemplate + """
            <tr>
                <td style="border-top: 1px solid #DFDFDF; color:#070e25; padding: 5px;">${name}</td>
                <td style="border-top: 1px solid #DFDFDF; color:#ffffff; padding: 5px; text-align: center; font-color:#7A272E; background: red;" class="center-align ${status}">${status}</td>
        	"""
    	}
    	if(status == "NOT_EXECUTED") {
        	emailTemplate = emailTemplate + """
            <tr>
                <td style="border-top: 1px solid #DFDFDF; color:#070e25; padding: 5px;">${name}</td>
                <td style="border-top: 1px solid #DFDFDF; color:#ffffff; padding: 5px; text-align: center; font-color:#3D4246; background: #4371cc;" class="center-align ${status}">${status}</td>
        	"""
    	}
        slackMessage = slackMessage + """
            ${i}. ${name} : ${slackemoji[status]}"""


        if ((stage.errorMessage).matches(".*[a-zA-Z]+.*")) { //Failure message
            def consoleLink = stage.consoleLink
            def errorMessage = stage.errorMessage
            emailTemplate = emailTemplate + """
                    <td style="border-top: 1px solid #DFDFDF; color:#070e25; padding: 5px; text-align: center;">${errorMessage}<br/><a href = "${consoleLink}">Click to check Console Log</a></td>
                </tr>
            """
            slackMessage = slackMessage + """
            Reason : ${errorMessage} >> <${consoleLink}|*ConsoleLog*> """
        }else{
            emailTemplate = emailTemplate + """<td style="border-top: 1px solid #DFDFDF; color:#070e25; padding: 5px;"></td></tr>"""
        } 
        if(status == "FAILED") break;
        i = i+1
    }
    emailTemplate = emailTemplate + "</table>" + "<h3>Volansys DevOps Team</h3>"

    return [emailTemplate: emailTemplate, slackMessage: slackMessage]    
}

@NonCPS
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}