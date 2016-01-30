<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>POC Demo</title>
    <link rel="shortcut icon" href="https://avatars1.githubusercontent.com/u/5185057">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

<script>
    $(function() {

        $("#tabs").tabs({
            activate: function(event, ui) {
                window.location.hash = ui.newPanel.attr('id');
            }
        });

        $(document).ready(function () {
            var sideBar = localStorage.getItem('activetab');
            if(sideBar === 'undefined'){
                $("#tabs").tabs("option", "active", 0);
            }else {

                $("#tabs").tabs("option", "active", [localStorage.getItem("activetab")]);
            }
        });
    });

    function saveData(){
        if (typeof(Storage) !== "undefined") {
            // Store
            localStorage.setItem("activetab", "0");
            // Retrieve
        }

    }

    function queryData(){
        if (typeof(Storage) !== "undefined") {
            // Store
            localStorage.setItem("activetab", "1");
            // Retrieve
        }
    }

    function queryPatient(){
        if(!document.getElementById("identifier").value || !document.getElementById("identifierType").value){
            document.getElementById("queryPatientWarnMsg").style.color = "red";
            document.getElementById("queryPatientWarnMsg").innerHTML = "Specify";
            return false;
        }
        else {
            document.getElementById("queryPatientWarnMsg").innerHTML = "";
            if (typeof(Storage) !== "undefined") {
                // Store
                localStorage.setItem("activetab", "3");
                // Retrieve
            }
        }
    }

    function queryFacility(){
        if(!document.getElementById("facilityName").value){
            document.getElementById("facilityWarnMsg").style.color = "red";
            document.getElementById("facilityWarnMsg").innerHTML = "Specify";
            return false;
        } else {
            document.getElementById("facilityWarnMsg").innerHTML = "";
            if (typeof(Storage) !== "undefined") {
                // Store
                localStorage.setItem("activetab", "4");
                // Retrieve
            }
        }
    }

    function queryProvider(){
        if(!document.getElementById("providerName").value){
            document.getElementById("providerWarnMsg").style.color = "red";
            document.getElementById("providerWarnMsg").innerHTML = "Specify";
            return false;
        } else {
            document.getElementById("providerWarnMsg").innerHTML = "";
            if (typeof(Storage) !== "undefined") {
                // Store
                localStorage.setItem("activetab", "5");
                // Retrieve
            }
        }
    }

    function createPatient(){

        if (typeof(Storage) !== "undefined") {
            // Store
            localStorage.setItem("activetab", "2");
            // Retrieve
        }
    }


</script>
</head>
<body>
<h2 font-family="Arial" align="center">POC Demo Application</h2>
<div id="tabs">
    <ul>
        <li><a href="#tabs-1">Create APHP Document</a></li>
        <li><a href="#tabs-2">Query Clinical Data</a></li>
        <li><a href="#tabs-3">Create Patient</a></li>
        <li><a href="#tabs-4">Query Patient</a></li>
        <li><a href="#tabs-5">Query Facility</a></li>
        <li><a href="#tabs-6">Query Provider</a></li>
        <li><a href="#tabs-6"></a></li>




    </ul>
    <div id="tabs-1">
            <div style="display:inline-block;border:1px solid green;" class="padding">

                <form:form method="POST" commandName="form" onsubmit="saveData()">
                    <table cellpadding="10">
                        <tr><td>Patient Identifier</td><td><input type="text" name="identifier"></td><td>Identifier Type</td><td><input type="text" name="identifierType"></td></tr>



                        <tr>
                            <form:hidden path="hiddenMessage"/>
                        </tr>
                        <tr><td></td><td></td></tr>
                        <tr>
                            <td><button type="submit" name="action" value="submit" font-family="Verdana,Arial,sans-serif; font-size: 1em;">Save Clinical Data</button></td>
                        </tr>

                    </table>
                    <table>
                        <tr><td>  <label id="saveData"></label></td><td></td></tr>
                    </table>
                    <input type="hidden" name="message" value="form1" />
                </form:form>
            </div>
        </div>
    <div id="tabs-2">
        <div style="display:inline-block;border:1px solid green;" class="padding">

            <form:form method="POST" commandName="query" onsubmit="queryData()">
                <table cellpadding="10">
                    <tr><td></td></tr>
                    <tr><td>Patient Identifier </td><td><input type="text" name="identifier"></td></tr>
                    <tr><td>Identifier Type </td><td><input type="text" name="identifierType"></td></tr>
                    <tr>
                        <td><button type="submit" name="action" value="query" font-family="Verdana,Arial,sans-serif; font-size: 1em;">Query Clinical Data</button></td>
                    </tr>
                    <tr>
                </table>
                <input type="hidden" name="message" value="form1" />
            </form:form>
        </div>
        <div style="display:inline-block;border:1px solid green;width:100%;height:100%" class="padding">
            <label id="queryData">${form.queryDataResult}</label>

        </div>
    </div>

    <div id="tabs-3">
            <div style="display:inline-block;border:1px solid green;" class="padding">
                <form:form method="POST" commandName="patient" onsubmit="createPatient()">
                    <table cellpadding="10">
                        <tr><td></td></tr>
                        <tr><td>Identifier </td><td><input type="text" name="identifier"></td></tr>
                        <tr><td>Identifier Type</td><td><input type="text" name="identifierType"></td></tr>

                        <tr><td>First Name </td><td><input type="text" name="firstName"></td></tr>
                        <tr><td>Middle Name </td><td><input type="text" name="mname"></td></tr>
                        <tr><td>Last Name </td><td><input type="text" name="lastName"></td></tr>
                        <tr></tr>
                        <tr>
                            <td><button type="submit" name="action" value="patient" font-family="Verdana,Arial,sans-serif; font-size: 1em;">Create Patient</button></td>
                        </tr>
                    </table>
                    <input type="hidden" name="message" value="form2" />
                </form:form>
            </div>
        <div style="display:inline-block;border:1px solid green;width:100%;height:100%" class="padding">

                                    <pre>
  <c:out value="${form.createPatientResult}" />
</pre>

        </div>


    </div>
    <div id="tabs-4">
        <div style="display:inline-block;border:1px solid green;" class="padding">

            <form:form method="POST" id="frm" commandName="search" onsubmit="queryPatient()">
                <table cellpadding="10">
                    <tr><td></td></tr>
                    <tr><td>Patient Identifier </td><td><input type="text" id="identifier" name="identifier"></td><td><label id="queryPatientWarnMsg"></label></td></tr>
                    <tr><td>Identifier Type </td><td><input type="text" id="identifierType" name="identifierType"></td><td><label id="queryPatientWarnMsg"></label></td></tr>
                    <tr></tr>
                    <tr>
                        <td><button type="submit" name="action" value="search" font-family="Verdana,Arial,sans-serif; font-size: 1em;">Query Patient</button></td>
                    </tr>
                    <tr>
                </table>
                <input type="hidden" name="message" value="form4" />



            </form:form>
        </div>
        <div style="display:inline-block;border:1px solid green;width:100%;height:100%" class="padding;">

                        <pre>
  <c:out value="${form.queryPatientResult}" />
</pre>

        </div>
    </div>

    <div id="tabs-5">
        <div style="display:inline-block;border:1px solid green;" class="padding">

            <form:form method="POST" id="queryFacility" commandName="search" onsubmit="return queryFacility()">
                <table cellpadding="10">
                    <tr><td></td></tr>
                    <tr><td>Facility Name </td><td><input type="text" id="facilityName" name="facilityName"></td><td><label id="facilityWarnMsg"></label></td></tr>
                    <tr><td>Maximum Responses</td><td><input type="text" id="maxresponses" name="maxresponses"></td></tr>
                    <tr></tr>
                    <tr>
                        <td><button type="submit" name="action" value="searchFacility" font-family="Verdana,Arial,sans-serif; font-size: 1em;">Query Facility</button></td>
                    </tr>
                    <tr>
                </table>
                <input type="hidden" name="message" value="form5" />



            </form:form>
        </div>
        <div style="display:inline-block;border:1px solid green;width:100%;height:100%" class="padding;">
            <pre>
  <c:out value="${form.queryFacilityResult}" />
</pre>
        </div>
    </div>

    <div id="tabs-6">
        <div style="display:inline-block;border:1px solid green;" class="padding">

            <form:form method="POST" id="queryProvider" commandName="search" onsubmit=" return queryProvider()">
                <table cellpadding="10">
                    <tr><td></td></tr>
                    <tr><td>Provider Name </td><td><input type="text" id="providerName" name="providerName"></td><td><label id="providerWarnMsg"></label></td></tr>
                    <tr><td>Maximum Responses</td><td><input type="text" id="maxProviderresponses" name="maxresponses"></td></tr>
                    <tr></tr>
                    <tr>
                        <td><button type="submit" name="action" value="searchProvider" font-family="Verdana,Arial,sans-serif; font-size: 1em;">Query Provider</button></td>
                    </tr>
                    <tr>
                </table>
                <input type="hidden" name="message" value="form4" />



            </form:form>
        </div>
        <div style="display:inline-block;border:1px solid green;width:100%;height:100%" class="padding;">

                        <pre>
  <c:out value="${form.queryProviderResult}" />
</pre>

        </div>
    </div>



</div>

</body>
</html>
