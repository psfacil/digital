package br.com.fiap.bean;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.fiap.bo.ConsultorBO;
import br.com.fiap.entity.Consultor;
import br.com.fiap.exception.DBException;
import br.com.fiap.util.BundleUtil;

@ManagedBean
public class ConsultorBean {

	private Consultor consultor;
	
	private ConsultorBO bo;
	
	@PostConstruct //Inicializar os objetos
	private void iniciar(){
		consultor = new Consultor();
		consultor.setDataNascimento(Calendar.getInstance());
		bo = new ConsultorBO();
	}
	
	public void validarNome(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		String nome = value.toString();
		
		if (!nome.trim().contains(" ")){
			throw new ValidatorException(new FacesMessage("Por favor, adicione o seu nome completo"));
		}
		
	}
	
	public String cadastrar(){
		FacesMessage msg;
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if (consultor.getCodigo() == 0){
				bo.cadastrar(consultor);
				String mensagem = BundleUtil.getMessageResourceString(
						context.getApplication().getMessageBundle(),
						"msg_add_success", null, 
						context.getViewRoot().getLocale());
				msg = new FacesMessage(mensagem);
			}else{
				bo.atualizar(consultor);
				String mensagem = BundleUtil.getMessageResourceString(
						context.getApplication().getMessageBundle(),
						"msg_update_success", null,
						context.getViewRoot().getLocale());
				msg = new FacesMessage(mensagem);
			}
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
		} catch (DBException e) {
			e.printStackTrace();
			msg = new FacesMessage("Deu ruim");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			return "consultor";
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		//Manter a mensagem após o redirect (nova requisição)
		FacesContext.getCurrentInstance().getExternalContext()
							.getFlash().setKeepMessages(true);
		return "lista-consultor?faces-redirect=true";
	}

	public Consultor getConsultor() {
		return consultor;
	}

	public void setConsultor(Consultor consultor) {
		this.consultor = consultor;
	}
	
}