package edu.netcracker.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.types.CollectionExpression;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.Visitor;
import edu.netcracker.center.domain.Form;
import edu.netcracker.center.domain.QForm;
import edu.netcracker.center.domain.QStudent;
import edu.netcracker.center.service.FormService;
import edu.netcracker.center.web.rest.util.HeaderUtil;
import edu.netcracker.center.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Form.
 */
@RestController
@RequestMapping("/api")
public class FormResource {

    private static final Logger log = LoggerFactory.getLogger(FormResource.class);

    private static final Predicate studentIsNull = QForm.form.notIn(new JPASubQuery()
        .from(QStudent.student)
        .where(QStudent.student.form.isNotNull())
        .list(QStudent.student.form));

    @Inject
    private FormService formService;

    /**
     * POST  /forms -> Create a new form.
     */
    @RequestMapping(value = "/forms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Form> createForm(@Valid @RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to save Form : {}", form);
        if (form.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("form", "idexists", "A new form cannot already have an ID")).body(null);
        }
        Form result = formService.save(form);
        return ResponseEntity.created(new URI("/api/forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("form", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forms -> Updates an existing form.
     */
    @RequestMapping(value = "/forms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Form> updateForm(@Valid @RequestBody Form form) throws URISyntaxException {
        log.debug("REST request to update Form : {}", form);
        if (form.getId() == null) {
            return createForm(form);
        }
        Form result = formService.save(form);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("form", form.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forms -> get all the forms.
     */
    @RequestMapping(value = "/forms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Form>> getAllForms(Pageable pageable,
                                                  @RequestParam(required = false) String filter,
                                                  @QuerydslPredicate(root = Form.class) Predicate predicate)
        throws URISyntaxException {
        log.debug("REST request to get a page of Forms");
        if ("student-is-null".equals(filter)) {
            log.debug("REST forms add filter: {}", filter);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            predicate = builder.and(studentIsNull);
        }
        Page<Form> page = formService.findAll(predicate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /forms/:id -> get the "id" form.
     */
    @RequestMapping(value = "/forms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Form> getForm(@PathVariable Long id) {
        log.debug("REST request to get Form : {}", id);
        Form form = formService.findOne(id);
        return Optional.ofNullable(form)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /forms/:id -> delete the "id" form.
     */
    @RequestMapping(value = "/forms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        log.debug("REST request to delete Form : {}", id);
        formService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("form", id.toString())).build();
    }
}
