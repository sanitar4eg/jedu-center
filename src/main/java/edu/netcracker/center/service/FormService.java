package edu.netcracker.center.service;

import edu.netcracker.center.domain.Form;

import java.util.List;

/**
 * Service Interface for managing Form.
 */
public interface FormService {

    /**
     * Save a form.
     * @return the persisted entity
     */
    public Form save(Form form);

    /**
     *  get all the forms.
     *  @return the list of entities
     */
    public List<Form> findAll();
    /**
     *  get all the forms where Student is null.
     *  @return the list of entities
     */
    public List<Form> findAllWhereStudentIsNull();

    /**
     *  get the "id" form.
     *  @return the entity
     */
    public Form findOne(Long id);

    /**
     *  delete the "id" form.
     */
    public void delete(Long id);
}
