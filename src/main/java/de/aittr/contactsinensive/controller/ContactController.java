package de.aittr.contactsinensive.controller;

import de.aittr.contactsinensive.dto.contact.CreateContactDto;
import de.aittr.contactsinensive.dto.contact.ReadContactDto;
import de.aittr.contactsinensive.dto.contact.UpdateContactDto;
import de.aittr.contactsinensive.security.model.CustomUserDetails;
import de.aittr.contactsinensive.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
@Tag(name = "Contact API", description = "API для управления контактами")
public class ContactController {

    private final ContactService contactService;

    @PreAuthorize("hasAnyAuthority()")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить контакт по ID",
            description = "Возвращает контакт с заданным идентификатором",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReadContactDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Контакт не найден")
            }
    )
    public ReadContactDto getContactById(@PathVariable Long id) {
        return contactService.getOrThrowById(id);
    }

    @PreAuthorize("hasAnyAuthority()")
    @GetMapping()
    @Operation(
            summary = "Получить все контакты",
            description = "Возвращает список всех доступных контактов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReadContactDto.class))
                    )
            }
    )
    public List<ReadContactDto> getAllContacts(@AuthenticationPrincipal @Parameter(hidden = true) CustomUserDetails user) {
        return contactService.findAll(user.user());
    }

    @PreAuthorize("hasAnyAuthority()")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать новый контакт",
            description = "Создает новый контакт и возвращает его данные",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для создания контакта",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateContactDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Контакт успешно создан",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReadContactDto.class))
                    )
            }
    )
    public ReadContactDto createContact(@RequestBody CreateContactDto createContactDto) {
        return contactService.create(createContactDto);
    }

    @PreAuthorize("hasAnyAuthority()")
    @PutMapping("/{contactId}")
    @Operation(
            summary = "Обновить контакт",
            description = "Обновляет существующий контакт",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для обновления контакта",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateContactDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Контакт успешно обновлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReadContactDto.class))
                    )
            }
    )
    public ReadContactDto updateContact(@RequestBody UpdateContactDto updateContactDto, @PathVariable long contactId) {
        return contactService.update(contactId, updateContactDto);
    }

    @PreAuthorize("hasAnyAuthority()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Удалить контакт",
            description = "Удаляет контакт с заданным идентификатором",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Контакт успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Контакт не найден")
            }
    )
    public void deleteContact(@PathVariable Long id) {
        contactService.delete(id);
    }
}
