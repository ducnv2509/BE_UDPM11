package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.response.Account.AccountDTO;
import ecom.udpm.vn.dto.response.Account.AccountResponse;
import ecom.udpm.vn.entity.Account;
import ecom.udpm.vn.entity.Employee;
import ecom.udpm.vn.entity.Role;
import ecom.udpm.vn.exception.AccountException;
import ecom.udpm.vn.repository.AccountRepository;
import ecom.udpm.vn.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {
	private AccountRepository accountRepository;

	private RoleRepository roleRepository;

	ModelMapper modelMapper;

	public Iterable<Account> getAll() {
		return accountRepository.findAllByIsDelete(false);
	}

	public Account save(AccountDTO accountDTO) {
		try {
			Account account = modelMapper.map(accountDTO, Account.class);
			if(!accountDTO.getPassword().isEmpty()) {
				account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));
			}
			account.setIsDelete(false);

			Employee employee = new Employee();
			employee.setAddress(accountDTO.getAddress());
			employee.setEmail(accountDTO.getEmail());
			employee.setPhone(accountDTO.getPhone());
			employee.setImage(accountDTO.getImage());
			employee.setFullName(accountDTO.getFullName());

			Set<Role> roles = new HashSet<>();
			for(String role : accountDTO.getRoleString()) {
				Role roleId = roleRepository.findRoleByName(role);
				roles.add(roleId);
			}

			employee.setAccount(account);

			account.setEmployee(List.of(employee));
			account.setRoles(roles);
			return accountRepository.save(account);
		} catch(Exception e) {
			throw new AccountException(e.getMessage(), e.getCause());
		}
	}

	public Account edit(AccountDTO accountDTO) {
		Account account = modelMapper.map(accountDTO, Account.class);

		Set<Role> roles = new HashSet<>();
		for(String role : accountDTO.getRoleString()) {
			Role roleId = roleRepository.findRoleByName(role);
			roles.add(roleId);
		}

		account.setRoles(roles);
		return accountRepository.save(account);
	}

	public void delete(Integer id) {
		Account account = accountRepository.findById(id).get();
		account.setIsDelete(true);
		accountRepository.save(account);
	}

	public AccountResponse getAllDetails(Integer id) {
		try {
			Account account = accountRepository.findById(id).get();
			AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
			if(!account.getEmployee().isEmpty()) {
				accountResponse.setFullName(account.getEmployee().get(0).getFullName());
				accountResponse.setImage(account.getEmployee().get(0).getImage());
				accountResponse.setEmail(account.getEmployee().get(0).getEmail());
				accountResponse.setPhone(account.getEmployee().get(0).getPhone());
				accountResponse.setAddress(account.getEmployee().get(0).getAddress());
			}
			if(!account.getRoles().isEmpty()) {
				accountResponse.setRoleIds(account.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
				accountResponse.setAuthorities(account.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
			}

			return accountResponse;
		} catch(Exception e) {
			e.printStackTrace();
			throw new NoSuchElementException("Tài khoản không tồn tại");
		}
	}

	public Page<Account> getPerPage(Integer size, Integer page) {
		return accountRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").descending()));
	}
}
