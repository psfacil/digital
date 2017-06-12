package br.com.fiap.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.com.fiap.repository.ProdutoRepository;
import br.com.fiap.to.Produto;

@ManagedBean
public class ProdutoBean {

	private Produto produto;
	
	private List<Produto> lista;
	
	private ProdutoRepository repository;
	
	@PostConstruct
	private void init(){
		repository = new ProdutoRepository();
		produto = new Produto();
		try {
			lista = repository.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String cadastrar(){
		FacesMessage msg;
		try {
			repository.cadastrar(produto);
			msg = new FacesMessage("Produto cadastrado!");
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage("Erro");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext()
			.getFlash().setKeepMessages(true);
		return "produto?faces-redirect=true";
	}
	
	public String atualizar(){
		FacesMessage msg;
		try {
			repository.atualizar(produto);
			msg = new FacesMessage("Produto Atualizado!");
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage("Erro");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext()
			.getFlash().setKeepMessages(true);
		return "produto?faces-redirect=true";
	}
	
	public String getProdutoLista(int codigo, String descricao,int quantidade, double valor ) {
		this.produto.setCodigo(codigo);
		this.produto.setDescricao(descricao);
		this.produto.setQuantidade(quantidade);
		this.produto.setValor(valor);
		return "produto?faces-redirect=true";
		
	}
	
	public String excluir(int cod){
		FacesMessage msg;
		try {
			repository.remover(cod);
			msg = new FacesMessage("Produto Excluido!");
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage("Erro");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext()
			.getFlash().setKeepMessages(true);
		return "produto?faces-redirect=true";
		
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getLista() {
		return lista;
	}

	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}
	
}
