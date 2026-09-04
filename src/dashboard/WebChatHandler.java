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
        h.append("<div class='container'><div class='chat-container'>");
        h.append("<div class='chat-messages' id='chatBox'>");
        h.append("<div class='chat-msg system'><strong>Welcome, ").append(HtmlTemplates.escapeHtml(user.getUsername())).append("!</strong><br>");
        h.append("<span style='color:var(--text-secondary);font-size:12px;'>Type your daily transactions in plain language or tap quick tags below.</span></div></div>");
        h.append("<div class='quick-chips'>");
        h.append("<span class='quick-chip' onclick=\"insertTag('[sale] ')\"><i class='ti ti-plus'></i> Sale</span>");
        h.append("<span class='quick-chip' onclick=\"insertTag('[expense] ')\"><i class='ti ti-minus'></i> Expense</span>");
        h.append("<span class='quick-chip' onclick=\"insertTag('[debt] ')\"><i class='ti ti-scale'></i> Debt</span>");
        h.append("<span class='quick-chip' onclick=\"insertTag('[payment] ')\"><i class='ti ti-cash'></i> Payment</span>");
        h.append("<span class='quick-chip' onclick=\"insertText('what is my profit')\"><i class='ti ti-chart-bar'></i> Profit</span>");
        h.append("<span class='quick-chip' onclick=\"insertText('who owes me')\"><i class='ti ti-users'></i> Debts</span>");
        h.append("</div>");
        h.append("<div class='chat-input-bar'>");
        h.append("<input type='text' id='chatInput' placeholder='e.g. Sold 5 bags of rice for N100,000' autocomplete='off'>");
        h.append("<button class='btn btn-primary' id='sendBtn'><i class='ti ti-send'></i> Send</button></div></div>");

        // Help FAB + Panel
        h.append("<button class='help-fab' id='helpBtn' title='Examples & Help'><i class='ti ti-help'></i></button>");
        h.append("<div class='sidebar-overlay' id='helpOverlay' style='display:none;'></div>");
        h.append("<div class='help-panel' id='helpPanel'>");
        h.append("<div style='display:flex;justify-content:space-between;align-items:center;margin-bottom:15px;'>");
        h.append("<h3 style='color:var(--text-primary);font-size:16px;font-weight:700;'>How to Record</h3>");
        h.append("<button id='helpClose' style='background:none;border:none;font-size:20px;cursor:pointer;color:#888;'>&times;</button></div>");
        h.append("<p style='font-size:12px;color:#888;margin-bottom:12px;'>Tap any example to auto-fill:</p>");
        h.append("<p style='font-size:11px;font-weight:600;color:#2e7d32;margin-bottom:6px;'>RECORD TRANSACTIONS</p>");
        String[] examples = {"Sold 5 bags of rice for N100,000", "Bought 2 cartons of Milo N35,000", "Oga Musa owes me N12,000", "Paid N5,000 for transport", "Received N6,000 from Mama Tope"};
        for (String ex : examples) h.append("<div class='help-example' data-text='").append(ex).append("'>").append(ex).append("</div>");
        h.append("<p style='font-size:11px;font-weight:600;color:#2e7d32;margin:12px 0 6px;'>FORCE A CATEGORY</p>");
        h.append("<div class='help-example' data-text='[sale] Rice to customer N20,000'>[sale] Rice to customer N20,000</div>");
        h.append("<div class='help-example' data-text='[debt] Oga Bello 3 bags N45,000'>[debt] Oga Bello 3 bags N45,000</div>");
        h.append("<p style='font-size:11px;font-weight:600;color:#2e7d32;margin:12px 0 6px;'>COMMANDS</p>");
        String[] cmds = {"show my dashboard", "what is my profit", "who owes me", "summary", "undo"};
        for (String cmd : cmds) h.append("<div class='help-example' data-text='").append(cmd).append("'>").append(cmd).append("</div>");
        h.append("</div></div>");

        // JS - using clean event listeners, no inline onclick, no quote escaping issues
        h.append("<script>\n");
        h.append("(function(){\n");
        h.append("var TOKEN='").append(token).append("';\n");
        h.append("var chatBox=document.getElementById('chatBox');\n");
        h.append("var chatInput=document.getElementById('chatInput');\n");
        h.append("var pending={};\n");
        h.append("var pid=0;\n\n");

        // Event listeners
        h.append("document.getElementById('sendBtn').addEventListener('click',sendMessage);\n");
        h.append("chatInput.addEventListener('keydown',function(e){if(e.key==='Enter')sendMessage();});\n");
        h.append("document.getElementById('helpBtn').addEventListener('click',toggleHelp);\n");
        h.append("document.getElementById('helpClose').addEventListener('click',toggleHelp);\n");
        h.append("document.getElementById('helpOverlay').addEventListener('click',toggleHelp);\n");
        h.append("document.querySelectorAll('.help-example').forEach(function(el){el.addEventListener('click',function(){chatInput.value=this.getAttribute('data-text');chatInput.focus();toggleHelp();});});\n\n");

        h.append("window.insertTag=function(tag){chatInput.value=tag;chatInput.focus();};\n");
        h.append("window.insertText=function(txt){chatInput.value=txt;sendMessage();};\n\n");
        h.append("function toggleHelp(){document.getElementById('helpPanel').classList.toggle('open');var o=document.getElementById('helpOverlay');o.style.display=o.style.display==='block'?'none':'block';}\n\n");

        h.append("function addMsg(html,cls){var d=document.createElement('div');d.className='chat-msg '+cls;d.innerHTML=html;chatBox.appendChild(d);chatBox.scrollTop=chatBox.scrollHeight;return d;}\n\n");

        h.append("function showTyping(){var d=document.createElement('div');d.className='typing';d.id='typ';d.innerHTML='<span></span><span></span><span></span>';chatBox.appendChild(d);chatBox.scrollTop=chatBox.scrollHeight;}\n");
        h.append("function hideTyping(){var t=document.getElementById('typ');if(t)t.remove();}\n\n");

        h.append("function showToast(msg){var t=document.createElement('div');t.className='toast success';t.textContent=msg;document.body.appendChild(t);setTimeout(function(){t.classList.add('show');},50);setTimeout(function(){t.classList.remove('show');setTimeout(function(){t.remove();},500);},4000);}\n\n");

        // d.counterparty is raw text the trader typed (e.g. "Oga Musa") - it goes into innerHTML below,
        // so it has to be escaped first or typed HTML would run as script in their own browser.
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

        // Confirm card - uses pending object to avoid quote escaping issues
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
        h.append("  ['SALE','EXPENSE','SUPPLY','DEBT','PAYMENT','DELIVERY'].forEach(function(t){html+='<option value=\"'+t+'\"'+(t===d.type?' selected':'')+'>'+t+'</option>';});\n");
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