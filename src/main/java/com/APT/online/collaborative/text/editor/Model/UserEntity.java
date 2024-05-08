package com.APT.online.collaborative.text.editor.Model;
import com.APT.online.collaborative.text.editor.Model.UserDocument;

import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password; // Hashed

	@OneToMany(mappedBy = "user")
	private List<UserDocument> userDocuments;

}
