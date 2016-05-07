package edu.netcracker.center.service.impl;

import edu.netcracker.center.service.FormService;
import edu.netcracker.center.domain.Form;
import edu.netcracker.center.repository.FormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Form.
 */
@Service
@Transactional
public class FormServiceImpl implements FormService{

    private final Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

    @Inject
    private FormRepository formRepository;

    /**
     * Save a form.
     * @return the persisted entity
     */
    public Form save(Form form) {
        log.debug("Request to save Form : {}", form);
        Form result = formRepository.save(form);
        return result;
    }

    /**
     *  get all the forms.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Form> findAll(Pageable pageable) {
        log.debug("Request to get all Forms");
        Page<Form> result = formRepository.findAll(pageable);
        return result;
    }


    /**
     *  get all the forms where Student is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Form> findAllWhereStudentIsNull() {
        log.debug("Request to get all forms where Student is null");
        return StreamSupport
            .stream(formRepository.findAll().spliterator(), false)
            .filter(form -> form.getStudent() == null)
            .collect(Collectors.toList());
    }

    /**
     *  get one form by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Form findOne(Long id) {
        log.debug("Request to get Form : {}", id);
        Form form = formRepository.findOne(id);
        return form;
    }

    /**
     *  delete the  form by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Form : {}", id);
        Form form = formRepository.findOne(id);
        deleteRelationFromStudent(form);
        formRepository.delete(form);
    }

    @Override
    public void delete(Form form) {
        log.debug("Request to delete Form : {}", form);
        deleteRelationFromStudent(form);
        formRepository.delete(form);
    }

    private void deleteRelationFromStudent(Form form) {
        form.getStudent().setForm(null);
        form.setStudent(null);
    }
}
