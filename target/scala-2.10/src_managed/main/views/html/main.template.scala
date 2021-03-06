
package views.html

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
import views.html._
/**/
object main extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(connected: String)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.36*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>Websocket Chat-Room</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*8.54*/routes/*8.60*/.Assets.at("stylesheets/bootstrap.css"))),format.raw/*8.99*/("""">
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*9.54*/routes/*9.60*/.Assets.at("stylesheets/main.css"))),format.raw/*9.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*10.59*/routes/*10.65*/.Assets.at("images/favicon.png"))),format.raw/*10.97*/("""">
        <script src=""""),_display_(Seq[Any](/*11.23*/routes/*11.29*/.Assets.at("javascripts/jquery-1.7.1.min.js"))),format.raw/*11.74*/("""" type="text/javascript"></script>
    </head>
    <body>
        
        <div class="topbar">
            <div class="fill">
                <div class="container">
                                  
                    """),_display_(Seq[Any](/*19.22*/if(connected != null)/*19.43*/ {_display_(Seq[Any](format.raw/*19.45*/("""
                        <p class="pull-right">
                            Logged in as """),_display_(Seq[Any](/*21.43*/connected)),format.raw/*21.52*/(""" —
                            <a href=""""),_display_(Seq[Any](/*22.39*/routes/*22.45*/.Application.index())),format.raw/*22.65*/("""">Disconnect</a>
                        </p>
                    """)))}/*24.23*/else/*24.28*/{_display_(Seq[Any](format.raw/*24.29*/("""
                        <form action=""""),_display_(Seq[Any](/*25.40*/routes/*25.46*/.Application.chatRoom())),format.raw/*25.69*/("""" class="pull-right">
                            <input id="username" name="username" class="input-small" type="text" placeholder="Username">
                            <button class="btn" type="submit">Sign in</button>
                        </form>
                    """)))})),format.raw/*29.22*/("""
                    
                </div>
            </div>
        </div>

        <div class="container">

            <div class="content">
                """),_display_(Seq[Any](/*38.18*/content)),format.raw/*38.25*/("""
            </div>

            <footer>
                <p>
                    <a href="http://www.playframework.com">www.playframework.com</a>
                </p>
            </footer>

        </div>
        
    </body>
</html>
"""))}
    }
    
    def render(connected:String,content:Html): play.api.templates.HtmlFormat.Appendable = apply(connected)(content)
    
    def f:((String) => (Html) => play.api.templates.HtmlFormat.Appendable) = (connected) => (content) => apply(connected)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Jan 20 18:17:32 CET 2017
                    SOURCE: /home/ubuntu-master/Downloads/playWebsocket/app/views/main.scala.html
                    HASH: a06ef65a2014b7cb8c9b4df52f66e2aba0a5743a
                    MATRIX: 778->1|906->35|1074->168|1088->174|1148->213|1239->269|1253->275|1308->309|1405->370|1420->376|1474->408|1535->433|1550->439|1617->484|1876->707|1906->728|1946->730|2072->820|2103->829|2180->870|2195->876|2237->896|2323->964|2336->969|2375->970|2451->1010|2466->1016|2511->1039|2818->1314|3018->1478|3047->1485
                    LINES: 26->1|29->1|36->8|36->8|36->8|37->9|37->9|37->9|38->10|38->10|38->10|39->11|39->11|39->11|47->19|47->19|47->19|49->21|49->21|50->22|50->22|50->22|52->24|52->24|52->24|53->25|53->25|53->25|57->29|66->38|66->38
                    -- GENERATED --
                */
            