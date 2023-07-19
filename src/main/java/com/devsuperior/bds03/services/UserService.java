package com.devsuperior.bds03.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.bds03.entities.Role;
import com.devsuperior.bds03.entities.User;
import com.devsuperior.bds03.projections.userDetailsProjection;
import com.devsuperior.bds03.repositories.UserRepository;



@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepository repository;
	
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         
			List<userDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
            if (result.size() == 0) {
            	throw new UsernameNotFoundException("Email not found");
            }
			
			User user = new User();
			user.setEmail(result.get(0).getUserName());
			user.setPassword(result.get(0).getPassword());
			for (userDetailsProjection projection : result) {
				user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
			}
			return user;
		}

	}

		



