package com.wesley.helpdesk.domain.enums;

public enum Perfil {
	
	//Enumerador com tipos de perfis que estarão disponíveis
	ADMIN(0, "ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO");
	
	private Integer codigo;
	private String descricao;

	private Perfil(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	//Método irá verificar se o perfil informado existe e se existir irá retornar
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Perfil perfil : Perfil.values()) {
			if(cod.equals(perfil.getCodigo())) {
				return perfil;
			}
		}
		
		throw new IllegalArgumentException("Perfil Inválido!");
	}
}
