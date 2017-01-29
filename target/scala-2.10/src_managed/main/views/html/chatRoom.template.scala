
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

    <div>
        <form action=""""),_display_(Seq[Any](/*26.24*/routes/*26.30*/.Application.chatRoom())),format.raw/*26.53*/("""" class="pull-right">
            <input id="username" name="username" class="input-small" type="text" placeholder="Opponent Name">
            <button class="btn" type="submit">Challenge</button>
        </form>
    </div>
    
    <script type="text/javascript" charset="utf-8" src=""""),_display_(Seq[Any](/*32.58*/routes/*32.64*/.Application.chatRoomJs(username))),format.raw/*32.97*/(""""></script>
    
""")))})),format.raw/*34.2*/("""

"""),format.raw/*36.10*/("""
    """),format.raw/*37.56*/("""
    """),format.raw/*38.91*/("""
"""),format.raw/*39.11*/("""
"""))}
    }
    
    def render(username:String): play.api.templates.HtmlFormat.Appendable = apply(username)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (username) => apply(username)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Jan 29 13:48:01 CET 2017
                    SOURCE: /home/ubuntu-master/Downloads/playWebsocket/app/views/chatRoom.scala.html
                    HASH: 73774fe5afb096106d8c04ffc9ddd5eadd1d36d5
                    MATRIX: 777->1|889->19|926->22|948->36|987->38|1548->563|1563->569|1608->592|1930->878|1945->884|2000->917|2049->935|2079->946|2112->1002|2145->1093|2174->1104
                    LINES: 26->1|29->1|31->3|31->3|31->3|54->26|54->26|54->26|60->32|60->32|60->32|62->34|64->36|65->37|66->38|67->39
                    -- GENERATED --
                */
            