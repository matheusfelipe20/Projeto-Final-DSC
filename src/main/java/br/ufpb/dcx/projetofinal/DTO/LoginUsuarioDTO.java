package br.ufpb.dcx.projetofinal.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginUsuarioDTO {
    
    @JsonProperty("email")
    @NotNull
    private String email;

    @JsonProperty("senha")
    @NotNull
    private String senha;

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginUsuarioDTO other = (LoginUsuarioDTO) obj;
        return Objects.equals(email, other.email);
    }
}
