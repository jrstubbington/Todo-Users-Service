package org.example.todo.accounts.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.todo.accounts.dto.ResponseContainerUserDto;
import org.example.todo.accounts.dto.ResponseContainerWorkspaceDto;
import org.example.todo.accounts.dto.WorkspaceDto;
import org.example.todo.accounts.service.UserService;
import org.example.todo.accounts.service.WorkspaceService;
import org.example.todo.common.exceptions.ImproperResourceSpecification;
import org.example.todo.common.exceptions.ResourceNotFoundException;
import org.example.todo.common.util.ResponseContainer;
import org.example.todo.common.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@Api(tags = "Workspace Management")
@Validated
@Slf4j
public class WorkspaceController implements WorkspaceManagementApi {

	private WorkspaceService workspaceService;

	private UserService userService;

	@Override
	public ResponseEntity<ResponseContainerUserDto> getUsersInWorkspace(UUID uuid) throws ImproperResourceSpecification, ResourceNotFoundException {
		return ResponseEntity.ok(userService.getAllUsersInWorkspaceResponse(uuid));
	}

	@Override
	public ResponseEntity<ResponseContainerWorkspaceDto> getWorkspacesV1(@Valid Optional<Integer> page, @Valid Optional<Integer> pageSize) {
//		ResponseContainerWorkspaceDto responseContainer = ResponseUtils.pageToDtoResponseContainer(workspaceService.getAllWorkspaces(PageRequest.of(page.orElse(0), pageSize.orElse(10))), WorkspaceDto.class);
//		return ResponseEntity.ok(responseContainer);
		ResponseContainer<WorkspaceDto> responseContainer = ResponseUtils.pageToDtoResponseContainer(workspaceService.getAllWorkspaces(PageRequest.of(page.orElse(0), pageSize.orElse(10))), WorkspaceDto.class);
		ResponseContainerWorkspaceDto responseContainerWorkspaceDto = new ResponseContainerWorkspaceDto();
		responseContainerWorkspaceDto.data(responseContainer.getData());
		return ResponseEntity.ok(responseContainerWorkspaceDto);
	}

	@Override
	public ResponseEntity<ResponseContainerWorkspaceDto> getWorkspaceByUUID(UUID uuid) throws ResourceNotFoundException {
		return ResponseEntity.ok(workspaceService.findWorkspaceByUuidResponse(uuid));
	}

	@Autowired
	public void setWorkspaceService(WorkspaceService workspaceService) {
		this.workspaceService = workspaceService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}