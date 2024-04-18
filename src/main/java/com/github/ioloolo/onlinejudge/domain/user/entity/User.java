package com.github.ioloolo.onlinejudge.domain.user.entity;

import java.io.Serial;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ioloolo.onlinejudge.common.validation.group.LengthGroup;
import com.github.ioloolo.onlinejudge.common.validation.group.MinMaxGroup;
import com.github.ioloolo.onlinejudge.common.validation.group.NotBlankGroup;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
@Document(collection = "users")
public class User implements UserDetails {

	@Serial
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotBlank(groups = NotBlankGroup.class, message = "아이디는 필수 입력값입니다.")
	@Size(groups = LengthGroup.class, min = 6, max = 24, message = "아이디는 6자 이상, 24자 이하로 입력해주세요.")
	private String username;

	@JsonIgnore
	@NotBlank(groups = NotBlankGroup.class, message = "비밀번호는 필수 입력값입니다.")
	private String password;

	@NotBlank(groups = NotBlankGroup.class, message = "이름은 필수 입력값입니다.")
	@Size(groups = LengthGroup.class, min = 2, max = 4, message = "이름은 2자 이상, 4자 이하로 입력해주세요.")
	private String realName;

	@Min(groups = MinMaxGroup.class, value = 1, message = "학년의 최솟값은 1학년 입니다.")
	@Max(groups = MinMaxGroup.class, value = 3, message = "학년의 최댓값은 3학년 입니다.")
	private int schoolGrade;

	@Min(groups = MinMaxGroup.class, value = 1, message = "반의 최솟값은 1반 입니다.")
	@Max(groups = MinMaxGroup.class, value = 99, message = "반의 최댓값은 99반 입니다.")
	private int schoolClass;

	@Min(groups = MinMaxGroup.class, value = 1, message = "번호의 최솟값은 1번 입니다.")
	@Max(groups = MinMaxGroup.class, value = 99, message = "번호의 최댓값은 99번 입니다.")
	private int schoolId;

	@Singular
	private Collection<Role> authorities;

	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities.stream().map(x -> new SimpleGrantedAuthority(x.name())).toList();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public enum Role {
		ROLE_USER, ROLE_ADMIN;

		public String raw() {

			return this.name().substring(5);
		}
	}

	public boolean isAdmin() {
		return authorities.stream().anyMatch(x -> x == Role.ROLE_ADMIN);
	}
}
