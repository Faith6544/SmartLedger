package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.UserDAO;
import model.User;

import java.io.IOException;
import java.io.OutputStream;

public class WebChatHandler implements HttpHandler {

    private UserDAO userDAO = new UserDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String token = path.replace("/chat/", "").replace("/", "");

        User user = userDAO.getUserByToken(token);
        if (user == null) {
            byte[] b = "Not found".getBytes();
            exchange.sendResponseHeaders(404, b.length);
            exchange.getResponseBody().write(b);
            exchange.getResponseBody().close();
            return;
        }

        String html = buildChatPage(user, token);
        byte[] bytes = html.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private String buildChatPage(User user, String token) {
        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head("Chat"));
        h.append(HtmlTemplates.nav(token, "chat"));
        h.append("<div class='container'>");

        h.append("<div class='chat-container'>");
        h.append("<div class='chat-messages' id='chatBox'>");
        h.append("<div class='chat-msg system'><span class='time'>System</span><br>");
        h.append("Welcome, ").append(HtmlTemplates.escapeHtml(user.getUsername())).append("! Type your transactions naturally.<br>");
        h.append("Tip: You can prefix with [sale], [debt], [expense], [supply], or [payment] to force a category.</div>");
        h.append("</div>");

        h.append("<div class='chat-input-bar'>");
        h.append("<input type='text' id='chatInput' placeholder='Type a transaction... e.g. Sold 5 bags of rice ₦100,000' autocomplete='off'>");
        h.append("<button class='btn btn-primary' onclick='sendMessage()'>Send</button>");
        h.append("</div>");
        h.append("</div>");

        h.append("</div>");

        // JavaScript
        h.append("<script>\n");
        h.append("var TOKEN='").append(token).append("';\n");
        h.append("var chatBox=document.getElementById('chatBox');\n");
        h.append("var chatInput=document.getElementById('chatInput');\n");
        h.append("chatInput.addEventListener('keydown',function(e){if(e.key==='Enter')sendMessage();});\n\n");

        h.append("function addMsg(text,cls){var d=document.createElement('div');d.className='chat-msg '+cls;d.innerHTML=text;chatBox.appendChild(d);chatBox.scrollTop=chatBox.scrollHeight;}\n\n");

        h.append("function sendMessage(){\n");
        h.append("  var msg=chatInput.value.trim();if(!msg)return;\n");
        h.append("  chatInput.value='';\n");
        h.append("  addMsg(msg,'user');\n");
        h.append("  fetch('/api/send',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},\n");
        h.append("    body:'message='+encodeURIComponent(msg)+'&token='+TOKEN})\n");
        h.append("  .then(r=>r.json()).then(d=>{\n");
        h.append("    if(d.isCommand){addMsg(d.response.replace(/\\n/g,'<br>'),'system');return;}\n");
        h.append("    if(!d.isTransaction){addMsg('Got it. (Not recorded as a transaction)','system');return;}\n");
        h.append("    showConfirm(d);\n");
        h.append("  }).catch(e=>addMsg('Error: '+e,'system'));\n");
        h.append("}\n\n");

        h.append("function showConfirm(d){\n");
        h.append("  var id='confirm-'+Date.now();\n");
        h.append("  var low=d.confidence==='LOW';\n");
        h.append("  var html='<div class=\"confirm-card\" id=\"'+id+'\">';\n");
        h.append("  html+='<strong>'+(low?'I\\'m not sure about this one:':'Transaction detected:')+'</strong><br>';\n");
        h.append("  html+='Category: <b>'+d.type+'</b> | Amount: <b>&#8358;'+d.amountFormatted+'</b>';\n");
        h.append("  if(d.counterparty)html+=' | Who: <b>'+d.counterparty+'</b>';\n");
        h.append("  html+='<br><br>';\n");

        // Category selector (always shown so user can change)
        h.append("  html+='Change category: <select id=\"cat-'+id+'\">';\n");
        h.append("  var types=['SALE','EXPENSE','SUPPLY','DEBT','PAYMENT'];\n");
        h.append("  for(var i=0;i<types.length;i++){html+='<option value=\"'+types[i]+'\"'+(types[i]===d.type?' selected':'')+'>'+types[i]+'</option>';}\n");
        h.append("  html+='</select><br>';\n");

        h.append("  html+='<div class=\"actions\">';\n");
        h.append("  html+='<button class=\"confirm-btn\" onclick=\"confirmTxn(\\''+id+'\\',\\''+d.description.replace(/'/g,'')+'\\','+d.amount+',\\''+(d.counterparty||'')+'\\')\">'+(low?'Save':'Confirm')+'</button>';\n");
        h.append("  html+='<button class=\"cancel-btn\" onclick=\"cancelTxn(\\''+id+'\\')\">'+'Cancel</button>';\n");
        h.append("  html+='</div></div>';\n");
        h.append("  addMsg(html,'');\n");
        h.append("}\n\n");

        h.append("function confirmTxn(id,desc,amount,counterparty){\n");
        h.append("  var type=document.getElementById('cat-'+id).value;\n");
        h.append("  fetch('/api/confirm',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},\n");
        h.append("    body:'type='+type+'&amount='+amount+'&description='+encodeURIComponent(desc)+'&counterparty='+encodeURIComponent(counterparty)+'&token='+TOKEN})\n");
        h.append("  .then(r=>r.json()).then(d=>{\n");
        h.append("    document.getElementById(id).innerHTML='<span style=\"color:#2e7d32;\">Recorded '+type+': &#8358;'+d.amountFormatted+(counterparty?' ('+counterparty+')':'')+'</span>';\n");
        h.append("  }).catch(e=>addMsg('Error saving: '+e,'system'));\n");
        h.append("}\n\n");

        h.append("function cancelTxn(id){document.getElementById(id).innerHTML='<span style=\"color:#888;\">Transaction cancelled.</span>';}\n");

        h.append("</script>\n");
        h.append(HtmlTemplates.footer());
        return h.toString();
    }
}
