package jetty_funeral.model;

public class Feedback {

	private int id;
	private int idFuneraria;
	private int idUser;
	private String avaliacao;
	private String comentario;

	public Feedback() {

	}

	public Feedback(int idFuneraria, int idUser, String avaliacao, String comentario) {
		this.idFuneraria = idFuneraria;
		this.idUser = idUser;
		this.avaliacao = avaliacao;
		this.comentario = comentario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdFuneraria() {
		return idFuneraria;
	}

	public void setIdFuneraria(int idFuneraria) {
		this.idFuneraria = idFuneraria;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
