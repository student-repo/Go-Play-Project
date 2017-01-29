
package views.js

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.js._
/**/
object chatRoom extends BaseScalaTemplate[play.api.templates.JavaScriptFormat.Appendable,Format[play.api.templates.JavaScriptFormat.Appendable]](play.api.templates.JavaScriptFormat) with play.api.templates.Template1[String,play.api.templates.JavaScriptFormat.Appendable] {

    /**/
    def apply/*1.2*/(username: String):play.api.templates.JavaScriptFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.20*/("""

$(function() """),format.raw/*3.14*/("""{"""),format.raw/*3.15*/("""
    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
 var chatSocket = new WS(""""),_display_(Seq[Any](/*5.28*/routes/*5.34*/.Application.chat(username).webSocketURL(request))),format.raw/*5.83*/("""")

    var sendMessage = function() """),format.raw/*7.34*/("""{"""),format.raw/*7.35*/("""
        chatSocket.send(JSON.stringify( """),format.raw/*8.41*/("""{"""),format.raw/*8.42*/("""nr: $("#nr").val()"""),format.raw/*8.60*/("""}"""),format.raw/*8.61*/(""" ))
        $("#nr").val('')

    """),format.raw/*11.5*/("""}"""),format.raw/*11.6*/("""

    var sendMessage2 = function() """),format.raw/*13.35*/("""{"""),format.raw/*13.36*/("""
        chatSocket.send(JSON.stringify( """),format.raw/*14.41*/("""{"""),format.raw/*14.42*/("""nrr: $("#nrr").val()"""),format.raw/*14.62*/("""}"""),format.raw/*14.63*/(""" ))
        $("#nrr").val('')

    """),format.raw/*17.5*/("""}"""),format.raw/*17.6*/("""

    var receiveEvent = function(event) """),format.raw/*19.40*/("""{"""),format.raw/*19.41*/("""
        var data = JSON.parse(event.data)
        // console.log(event.data);

        // Handle errors
        if(data.error) """),format.raw/*24.24*/("""{"""),format.raw/*24.25*/("""
            chatSocket.close()
            $("#onError span").text(data.error)
            $("#onError").show()
            return
        """),format.raw/*29.9*/("""}"""),format.raw/*29.10*/(""" 
        else """),format.raw/*30.14*/("""{"""),format.raw/*30.15*/("""
            $("#onChat").show()
            """),format.raw/*32.13*/("""}"""),format.raw/*32.14*/("""        
            // Create the message element       
	        var el = $('<div class="message"><p style="font-size:16px"></p></div>')
	        $("p", el).text(data.message)
	        $(el).addClass('me')
	        $('#messages').append(el) 
    """),format.raw/*38.5*/("""}"""),format.raw/*38.6*/("""

    var handleReturnKey = function(e) """),format.raw/*40.39*/("""{"""),format.raw/*40.40*/("""
        if(e.charCode == 13 || e.keyCode == 13) """),format.raw/*41.49*/("""{"""),format.raw/*41.50*/("""
            e.preventDefault()
            sendMessage()
        """),format.raw/*44.9*/("""}"""),format.raw/*44.10*/("""
    """),format.raw/*45.5*/("""}"""),format.raw/*45.6*/("""
    var handleReturnKey2 = function(e) """),format.raw/*46.40*/("""{"""),format.raw/*46.41*/("""
        if(e.charCode == 13 || e.keyCode == 13) """),format.raw/*47.49*/("""{"""),format.raw/*47.50*/("""
            e.preventDefault()
            sendMessage2()
        """),format.raw/*50.9*/("""}"""),format.raw/*50.10*/("""
    """),format.raw/*51.5*/("""}"""),format.raw/*51.6*/("""

    $("#nr").keypress(handleReturnKey)
    $("#nrr").keypress(handleReturnKey2)


    chatSocket.onmessage = receiveEvent

"""),format.raw/*59.1*/("""}"""),format.raw/*59.2*/(""")
"""))}
    }
    
    def render(username:String): play.api.templates.JavaScriptFormat.Appendable = apply(username)
    
    def f:((String) => play.api.templates.JavaScriptFormat.Appendable) = (username) => apply(username)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Jan 29 13:48:02 CET 2017
                    SOURCE: /home/ubuntu-master/Downloads/playWebsocket/app/views/chatRoom.scala.js
                    HASH: 2b83a453544923491e57fed3dcad11564cefd473
                    MATRIX: 797->1|915->19|957->34|985->35|1111->126|1125->132|1195->181|1259->218|1287->219|1355->260|1383->261|1428->279|1456->280|1517->314|1545->315|1609->351|1638->352|1707->393|1736->394|1784->414|1813->415|1875->450|1903->451|1972->492|2001->493|2157->621|2186->622|2353->762|2382->763|2425->778|2454->779|2527->824|2556->825|2831->1073|2859->1074|2927->1114|2956->1115|3033->1164|3062->1165|3155->1231|3184->1232|3216->1237|3244->1238|3312->1278|3341->1279|3418->1328|3447->1329|3541->1396|3570->1397|3602->1402|3630->1403|3782->1528|3810->1529
                    LINES: 26->1|29->1|31->3|31->3|33->5|33->5|33->5|35->7|35->7|36->8|36->8|36->8|36->8|39->11|39->11|41->13|41->13|42->14|42->14|42->14|42->14|45->17|45->17|47->19|47->19|52->24|52->24|57->29|57->29|58->30|58->30|60->32|60->32|66->38|66->38|68->40|68->40|69->41|69->41|72->44|72->44|73->45|73->45|74->46|74->46|75->47|75->47|78->50|78->50|79->51|79->51|87->59|87->59
                    -- GENERATED --
                */
            