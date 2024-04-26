package com.APT.online.collaborative.text.editor.Model;

import com.APT.online.collaborative.text.editor.UserDocument;
import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userid;

	private String username;

	private String password;

	@OneToMany(mappedBy = "user")
	private List<UserDocument> userDocuments;

}
