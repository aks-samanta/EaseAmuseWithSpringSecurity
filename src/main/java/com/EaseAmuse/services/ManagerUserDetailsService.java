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

import com.EaseAmuse.models.Manager;

import com.EaseAmuse.repositories.ManagerRepo;

@Service
public class ManagerUserDetailsService implements UserDetailsService {

	@Autowired
	private ManagerRepo managerRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Manager> opt = managerRepo.findByEmail(username);

		if (opt.isPresent()) {

			Manager manager = opt.get();

			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(manager.getRole());
			authorities.add(sga);

			return new User(manager.getEmail(), manager.getPassword(), authorities);

		} else
			throw new BadCredentialsException("User Details not found with this username: " + username);

	}
}
