package br.com.vendapedido.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import br.com.vendapedido.controller.PedidoEdicao;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

import br.com.vendapedido.model.Pedido;
import br.com.vendapedido.util.jsf.FacesUtil;
import br.com.vendapedido.util.mail.Mailer;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	 
	public void enviarPedido() {
		MailMessage message = mailer.novaMensagem();
		
		message.to("teste.vendas1@gmail.com")
			.subject("Pedido " + this.pedido.getId())
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))// vai pegar o pedido.template para jogar no email
			.put("pedido", this.pedido)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
		
		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
	}
}
