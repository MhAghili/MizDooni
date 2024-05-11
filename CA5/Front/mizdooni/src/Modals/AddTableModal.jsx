import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const AddTableModal = ({
  show,
  handleClose,
  restaurantName,
  managerUsername,
}) => {
  const [tableNumber, setTableNumber] = useState("");
  const [seatsNumber, setSeatsNumber] = useState("");
  const submitHndlr = async () => {
    try {
      const reqBody = {
        tableNumber: tableNumber,
        restaurantName: restaurantName,
        managerUsername: managerUsername,
        seatsNumber: seatsNumber,
      };
      const response = await fetch("http://127.0.0.1:8080/table", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(reqBody),
      });
      if (response.ok) {
        console.log("Table added successfully");
        handleClose();
      } else {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.log(error);
    }
    setTableNumber("");
    setSeatsNumber("");
  };
  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Add Table</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3" controlId="tableNumber">
            <Form.Label>Table Number</Form.Label>
            <Form.Control
              type="number"
              placeholder="Enter table number"
              value={tableNumber}
              onChange={(e) => setTableNumber(e.target.value)}
              pattern="[0-9]*"
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="seatsNumber">
            <Form.Label>Number of Seats</Form.Label>
            <Form.Control
              type="number"
              placeholder="Enter number of seats"
              onChange={(e) => setSeatsNumber(e.target.value)}
              value={seatsNumber}
              pattern="[0-9]*"
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="danger" onClick={submitHndlr}>
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default AddTableModal;
