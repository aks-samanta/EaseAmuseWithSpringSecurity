package com.EaseAmuse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.EaseAmuse.models.Admin;
import com.EaseAmuse.repositories.AdminRepo;

@Service
public class AdminUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepo adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Admin> opt = adminRepo.findByEmail(username);

		if (opt.isPresent()) {

			Admin admin = opt.get();

			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(admin.getRole());
			authorities.add(sga);

			return new User(admin.getEmail(), admin.getPassword(), authorities);

		} else
			throw new BadCredentialsException("User Details not found with this username: " + username);

	}

}
