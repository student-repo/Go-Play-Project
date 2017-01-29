
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
object chatRoom extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(username: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.20*/("""

"""),_display_(Seq[Any](/*3.2*/main(username)/*3.16*/ {_display_(Seq[Any](format.raw/*3.18*/("""
    
    <div class="page-header">
        <h1>WebSocket Logger</h1>
    </div>
    
    <div id="onError" class="alert-message error">
        <p>
            <strong>Oops!</strong> <span></span>
        </p>
    </div>
    
    <div id="onChat" class="row">
    	<input id="nr" placeholder="Give a position for a stone"></input>

        <input id="nrr" placeholder="Challenge"></input>

    	<div class="span10" id="main">
     		<div id="messages"></div>
   		</div>        
    </div>
    <script type="text/javascript" charset="utf-8" src=""""),_display_(Seq[Any](/*24.58*/routes/*24.64*/.Application.chatRoomJs(username))),format.raw/*24.97*/(""""></script>
    
""")))})),format.raw/*26.2*/("""

"""),format.raw/*28.10*/("""
    """),format.raw/*29.56*/("""
    """),format.raw/*30.91*/("""
"""),format.raw/*31.11*/("""
"""))}
    }
    
    def render(username:String): play.api.templates.HtmlFormat.Appendable = apply(username)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (username) => apply(username)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Jan 29 21:10:55 CET 2017
                    SOURCE: /home/ubuntu-master/Downloads/playWebsocket/app/views/chatRoom.scala.html
                    HASH: 041be651267d693f1ed3ba6d6f72d39ed1d737e0
                    MATRIX: 777->1|889->19|926->22|948->36|987->38|1571->586|1586->592|1641->625|1690->643|1720->654|1753->710|1786->801|1815->812
                    LINES: 26->1|29->1|31->3|31->3|31->3|52->24|52->24|52->24|54->26|56->28|57->29|58->30|59->31
                    -- GENERATED --
                */
            