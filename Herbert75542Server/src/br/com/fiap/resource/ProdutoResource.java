package br.com.fiap.resource;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.fiap.bo.ProdutoBO;
import br.com.fiap.entity.Produto;
import br.com.fiap.exception.CommitErrorException;

@Path("/produto")
public class ProdutoResource {

	private ProdutoBO bo = new ProdutoBO();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadastrar(Produto produto, @Context UriInfo uriInfo){
		try {
			bo.cadastrar(produto);
			UriBuilder url = UriBuilder.fromPath(uriInfo.getPath());
			url.path(String.valueOf(produto.getCodigo()));
			return Response.created(url.build()).build();
		} catch (CommitErrorException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Produto produto, @PathParam("id") int id){
		try {
			produto.setCodigo(id);
			bo.atualizar(produto);
			return Response.ok().build();
		} catch (CommitErrorException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public void remover(@PathParam("id") int id){
		try {
			bo.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Produto> lista(){
		return bo.listar();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Produto buscar(int cod){
		return bo.buscar(cod);
	}
	
}








