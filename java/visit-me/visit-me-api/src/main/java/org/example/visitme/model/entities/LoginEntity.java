package org.example.visitme.model.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@RequiredArgsConstructor
@Table(name = "login_tb")
@EqualsAndHashCode(of = "id")
public class LoginEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @Column(name = "recovery_password_question")
    private String recoveryPasswordQuestion;

    @Column(name = "recovery_password_answer")
    private String recoveryPasswordAnswer;

    private Boolean actived;


}
