package dashboard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.UserDAO;
import java.io.IOException;
import model.User;

public class WebChatHandler implements HttpHandler {

    private UserDAO userDAO = new UserDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String token = path.replace("/chat/", "").replace("/", "");
        User user = userDAO.getUserByToken(token);
        if (user == null) { exchange.getResponseHeaders().set("Location", "/auth/login"); exchange.sendResponseHeaders(302, -1); return; }
        byte[] bytes = buildChatPage(user, token).getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    private String buildChatPage(User user, String token) {
        StringBuilder h = new StringBuilder();
        h.append(HtmlTemplates.head("Chat"));
        h.append(HtmlTemplates.fullNav(token, "chat", user.getBusinessName()));
        
        h.append("<div class='container' style='max-width:800px;margin:0 auto;padding:16px 20px;'>");
        h.append("<div style='background:var(--bg-surface);border:1px solid var(--border-rule);border-radius:8px;padding:16px;'>");
        h.append("<div class='chat-messages' id='chatBox' style='min-height:340px;max-height:420px;overflow-y:auto;padding:8px 4px;'>");
        h.append("<div class='chat-msg system' style='font-family:Times New Roman, serif;'>");
        h.append("<strong>WELCOME, ").append(HtmlTemplates.escapeHtml(user.getUsername()).toUpperCase()).append("</strong><br>");
        h.append("<span style='color:var(--text-secondary);font-size:13px;'>Enter transactions in natural language or tap quick tags below.</span>");
        h.append("</div></div>");

        // Quick chips - cleaner layout
        h.append("<div style='display:flex;flex-wrap:wrap;gap:6px;margin:10px 0 12px;'>");
        String[][] chips = {
            {"[sale] ", "SALE", "#16a34a"},
            {"[expense] ", "EXPENSE", "#dc2626"},
            {"[debt] ", "DEBT", "#9333ea"},
            {"[payment] ", "PAYMENT", "#2563eb"},
            {"what is my profit", "PROFIT", "#ea580c"},
            {"who owes me", "DEBTORS", "#6A1B9A"}
        };
        for (String[] chip : chips) {
            String style = "font-family:Times New Roman, serif;padding:4px 12px;border:1px solid var(--border-rule);border-radius:20px;font-size:11px;font-weight:600;cursor:pointer;background:var(--bg-surface);color:var(--text-primary);transition:all 0.15s;";
            h.append("<span class='quick-chip' data-tag='").append(chip[0]).append("' style='").append(style).append("'>").append(chip[1]).append("</span>");
        }
        h.append("</div>");

        // Input bar
        h.append("<div style='display:flex;gap:8px;background:var(--bg-surface);padding:6px;border:1px solid var(--border-rule);border-radius:8px;'>");
        h.append("<input type='text' id='chatInput' placeholder='e.g. Sold 5 bags of rice for N100,000' autocomplete='off' style='flex:1;padding:8px 14px;border:1px solid var(--border-rule);border-radius:6px;font-family:Times New Roman, serif;font-size:14px;color:var(--text-primary);background:var(--bg-surface);outline:none;transition:border 0.2s;'>");
        h.append("<button class='btn btn-primary' id='sendBtn' style='font-family:Times New Roman, serif;padding:8px 18px;border-radius:6px;font-size:12px;font-weight:600;'><i class='ti ti-send'></i> SEND</button>");
        h.append("</div>");
        h.append("</div></div>");

        // Help FAB + Panel
        h.append("<button class='help-fab' id='helpBtn' title='Examples & Help' style='font-family:Times New Roman, serif;'><i class='ti ti-help'></i></button>");
        h.append("<div class='sidebar-overlay' id='helpOverlay' style='display:none;'></div>");
        h.append("<div class='help-panel' id='helpPanel' style='font-family:Times New Roman, serif;'>");
        h.append("<div style='display:flex;justify-content:space-between;align-items:center;margin-bottom:14px;border-bottom:1px solid var(--border-rule);padding-bottom:6px;'>");
        h.append("<h3 style='font-family:Times New Roman, serif;font-size:14px;font-weight:700;'>💡 Examples</h3>");
        h.append("<button id='helpClose' style='background:none;border:1px solid var(--border-rule);font-size:16px;cursor:pointer;border-radius:4px;padding:0 8px;'>&times;</button></div>");
        h.append("<p style='font-size:12px;color:var(--text-muted);margin-bottom:10px;'>Tap an example to auto-fill:</p>");
        
        String[] examples = {
            "Sold 5 bags of rice for N100,000",
            "Bought 2 cartons of Milo N35,000",
            "Oga Musa owes me N12,000",
            "Paid N5,000 for transport",
            "Received N6,000 from Mama Tope",
            "[sale] Rice to customer N20,000",
            "[debt] Oga Bello 3 bags N45,000",
            "what is my profit",
            "who owes me"
        };
        for (String ex : examples) {
            h.append("<div class='help-example' data-text='").append(ex).append("' style='font-family:Times New Roman, serif;padding:6px 12px;margin:4px 0;background:var(--bg-subtle);border:1px solid var(--border-rule);border-radius:4px;font-size:13px;cursor:pointer;transition:all 0.15s;'>").append(ex).append("</div>");
        }
        h.append("</div>");

        // JavaScript
        h.append("<script>\n");
        h.append("(function(){\n");
        h.append("var TOKEN='").append(token).append("';\n");
        h.append("var chatBox=document.getElementById('chatBox');\n");
        h.append("var chatInput=document.getElementById('chatInput');\n");
        h.append("var pending={};\n");
        h.append("var pid=0;\n\n");

        h.append("document.getElementById('sendBtn').addEventListener('click',sendMessage);\n");
        h.append("chatInput.addEventListener('keydown',function(e){if(e.key==='Enter')sendMessage();});\n");
        h.append("document.getElementById('helpBtn').addEventListener('click',toggleHelp);\n");
        h.append("document.getElementById('helpClose').addEventListener('click',toggleHelp);\n");
        h.append("document.getElementById('helpOverlay').addEventListener('click',toggleHelp);\n");
        h.append("document.querySelectorAll('.help-example').forEach(function(el){el.addEventListener('click',function(){chatInput.value=this.getAttribute('data-text');chatInput.focus();toggleHelp();});});\n");
        h.append("document.querySelectorAll('.quick-chip').forEach(function(el){el.addEventListener('click',function(){var tag=this.getAttribute('data-tag');chatInput.value=tag;chatInput.focus();});});\n\n");

        h.append("function toggleHelp(){document.getElementById('helpPanel').classList.toggle('open');var o=document.getElementById('helpOverlay');o.style.display=o.style.display==='block'?'none':'block';}\n\n");
        h.append("function addMsg(html,cls){var d=document.createElement('div');d.className='chat-msg '+cls;d.innerHTML=html;chatBox.appendChild(d);chatBox.scrollTop=chatBox.scrollHeight;return d;}\n\n");
        h.append("function showTyping(){var d=document.createElement('div');d.className='typing';d.id='typ';d.innerHTML='<span></span><span></span><span></span>';chatBox.appendChild(d);chatBox.scrollTop=chatBox.scrollHeight;}\n");
        h.append("function hideTyping(){var t=document.getElementById('typ');if(t)t.remove();}\n\n");
        h.append("function showToast(msg){var t=document.createElement('div');t.className='toast success';t.textContent=msg;document.body.appendChild(t);setTimeout(function(){t.classList.add('show');},50);setTimeout(function(){t.classList.remove('show');setTimeout(function(){t.remove();},500);},4000);}\n\n");
        h.append("function escapeHtml(s){var d=document.createElement('div');d.textContent=s;return d.innerHTML;}\n\n");

        h.append("function sendMessage(){\n");
        h.append("  var msg=chatInput.value.trim();\n");
        h.append("  if(!msg)return;\n");
        h.append("  chatInput.value='';\n");
        h.append("  addMsg(msg,'user');\n");
        h.append("  showTyping();\n");
        h.append("  fetch('/api/send',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},body:'message='+encodeURIComponent(msg)+'&token='+TOKEN})\n");
        h.append("  .then(function(r){return r.json();})\n");
        h.append("  .then(function(d){\n");
        h.append("    hideTyping();\n");
        h.append("    if(d.isCommand){addMsg(d.response.replace(/\\n/g,'<br>'),'system');return;}\n");
        h.append("    if(!d.isTransaction){var casualReplies=['Got it.','Noted!','Alright.','Okay, noted.','Gotcha.'];addMsg(casualReplies[Math.floor(Math.random()*casualReplies.length)],'system');return;}\n");
        h.append("    showConfirm(d);\n");
        h.append("  })\n");
        h.append("  .catch(function(e){hideTyping();addMsg('Error: '+e,'system');});\n");
        h.append("}\n\n");

        h.append("function showConfirm(d){\n");
        h.append("  var id='p'+(pid++);\n");
        h.append("  pending[id]=d;\n");
        h.append("  var low=d.confidence==='LOW';\n");
        h.append("  var div=document.createElement('div');\n");
        h.append("  div.className='confirm-card';\n");
        h.append("  div.id=id;\n");
        h.append("  var html='<strong>'+(low?'Not sure about this one:':'Transaction detected:')+'</strong><br>';\n");
        h.append("  html+='Category: <b>'+d.type+'</b> | Amount: <b>\\u20A6'+d.amountFormatted+'</b>';\n");
        h.append("  if(d.counterparty)html+=' | Who: <b>'+escapeHtml(d.counterparty)+'</b>';\n");
        h.append("  html+='<br><br>Change category: <select id=\"sel-'+id+'\">';\n");
        h.append("  ['SALE','EXPENSE','SUPPLY','DEBT','PAYMENT','DELIVERY','PERSONAL'].forEach(function(t){html+='<option value=\"'+t+'\"'+(t===d.type?' selected':'')+'>'+t+'</option>';});\n");
        h.append("  html+='</select><br><div class=\"actions\">';\n");
        h.append("  html+='<button class=\"confirm-btn\" data-id=\"'+id+'\">Confirm</button>';\n");
        h.append("  html+='<button class=\"cancel-btn\" data-id=\"'+id+'\">Cancel</button>';\n");
        h.append("  html+='</div>';\n");
        h.append("  div.innerHTML=html;\n");
        h.append("  chatBox.appendChild(div);\n");
        h.append("  chatBox.scrollTop=chatBox.scrollHeight;\n");
        h.append("  div.querySelector('.confirm-btn').addEventListener('click',function(){confirmTxn(this.getAttribute('data-id'));});\n");
        h.append("  div.querySelector('.cancel-btn').addEventListener('click',function(){cancelTxn(this.getAttribute('data-id'));});\n");
        h.append("}\n\n");

        h.append("function confirmTxn(id){\n");
        h.append("  var d=pending[id];\n");
        h.append("  if(!d)return;\n");
        h.append("  var type=document.getElementById('sel-'+id).value;\n");
        h.append("  var guessed=(type!==d.type)?'&guessed='+d.type:'';\n");
        h.append("  var cp=d.counterparty||'';\n");
        h.append("  fetch('/api/confirm',{method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},body:'type='+type+'&amount='+d.amount+'&description='+encodeURIComponent(d.description)+'&counterparty='+encodeURIComponent(cp)+'&token='+TOKEN+guessed})\n");
        h.append("  .then(function(r){return r.json();})\n");
        h.append("  .then(function(res){\n");
        h.append("    document.getElementById(id).innerHTML='<span style=\"color:#2e7d32;\">\\u2705 Recorded '+type+': \\u20A6'+res.amountFormatted+(cp?' ('+escapeHtml(cp)+')':'')+'</span>';\n");
        h.append("    showToast(type+' recorded: \\u20A6'+res.amountFormatted);\n");
        h.append("    delete pending[id];\n");
        h.append("  })\n");
        h.append("  .catch(function(e){addMsg('Error: '+e,'system');});\n");
        h.append("}\n\n");

        h.append("function cancelTxn(id){document.getElementById(id).innerHTML='<span style=\"color:#888;\">Transaction cancelled.</span>';delete pending[id];}\n");
        h.append("})();\n");
        h.append("</script>\n");
        h.append(HtmlTemplates.footer());
        return h.toString();
    }
}