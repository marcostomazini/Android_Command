package com.arquitetaweb.restaurantes.model;

import com.google.gson.annotations.SerializedName;

public class ConfiguracoesModel {
	
	@SerializedName("UrlServico")
	public String urlServico;
	
	@SerializedName("PortaServico")
    public Integer portaServico;

    public String getUrlServico() {
        return urlServico;
    }

    public void setUrlServico(String urlServidor) {
        this.urlServico = urlServidor;
    }

    public Integer getPortaServico() {
        return portaServico;
    }

    public void setPortaServico(Integer portaServico) {
        this.portaServico = portaServico;
    }
}
