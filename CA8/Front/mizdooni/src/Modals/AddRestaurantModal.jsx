import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const AddRestaurantModal = ({ show, handleClose }) => {
  const [name, setName] = useState("");
  const [type, setType] = useState("");
  const [description, setDescription] = useState("");
  const [city, setCity] = useState("");
  const [country, setCountry] = useState("");
  const [street, setStreet] = useState("");
  const [startHour, setStartHour] = useState("");
  const [endHour, setEndHour] = useState("");
  const userName = localStorage.getItem("username");
  const submitHandler = async () => {
    try {
      const requestBody = {
        name: name,
        type: type,
        description: description,
        startTime: startHour,
        endTime: endHour,
        address: {
          country: country,
          city: city,
          street: street,
        },
        managerUsername: userName,
      };

      const response = await fetch("http://127.0.0.1:8093/restaurants", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",

          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(requestBody),
      });
      if (response.ok) {
        toast.success("Restaurant added successfully");
        setName("");
        setType("");
        setDescription("");
        setCity("");
        setCountry("");
        setStreet("");
        setStartHour("");
        setEndHour("");

        handleClose();
      } else {
        const errorMessage = await response.text();
        toast.error(errorMessage);
        throw new Error(errorMessage);
      }
      console.log(requestBody);
    } catch (error) {}
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Add Table</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3" controlId="name">
            <Form.Label>Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter table name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="type">
            <Form.Label>Type</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter type"
              value={type}
              onChange={(e) => setType(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="description">
            <Form.Label>Description</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="country">
            <Form.Label>Country</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter country"
              value={country}
              onChange={(e) => setCountry(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="city">
            <Form.Label>City</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter city"
              value={city}
              onChange={(e) => setCity(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="street">
            <Form.Label>Street</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter street"
              value={street}
              onChange={(e) => setStreet(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="startHour">
            <Form.Label>Start Hour</Form.Label>
            <Form.Control
              type="time"
              value={startHour}
              onChange={(e) => setStartHour(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="endHour">
            <Form.Label>End Hour</Form.Label>
            <Form.Control
              type="time"
              value={endHour}
              onChange={(e) => setEndHour(e.target.value)}
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="danger" onClick={submitHandler}>
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default AddRestaurantModal;
