import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const AddReviewModal = ({ show, handleClose, restaurantName, username }) => {
  const [ambianceScore, setAmbianceScore] = useState("");
  const [serviceScore, setServiceScore] = useState("");
  const [foodScore, setFoodScore] = useState("");
  const [overallScore, setOverallScore] = useState("");
  const [comment, setComment] = useState("");

  const submitHandler = async () => {
    try {
      const requestBody = {
        ambianceRate: ambianceScore,
        serviceRate: serviceScore,
        foodRate: foodScore,
        overallRate: overallScore,
        comment: comment,
        restaurantName: restaurantName,
        username: username,
        // Add any additional fields you need here
      };
      console.log(requestBody);
      const response = await fetch("http://127.0.0.1:8080/reviews", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
      });

      if (response.ok) {
        console.log("Review added successfully");
        toast.success("Review added successfully");
        setAmbianceScore("");
        setServiceScore("");
        setFoodScore("");
        setOverallScore("");
        setComment("");
        handleClose();
      } else {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      {" "}
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Add Review</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="ambianceScore">
              <Form.Label>Ambiance Score</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Ambiance score"
                value={ambianceScore}
                onChange={(e) => setAmbianceScore(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="serviceScore">
              <Form.Label>Service Score</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter service score"
                value={serviceScore}
                onChange={(e) => setServiceScore(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="foodScore">
              <Form.Label>Food Score</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter food score"
                value={foodScore}
                onChange={(e) => setFoodScore(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="overallScore">
              <Form.Label>Overall Score</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter overall score"
                value={overallScore}
                onChange={(e) => setOverallScore(e.target.value)}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="comment">
              <Form.Label>Comment</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                placeholder="Enter your comment"
                value={comment}
                onChange={(e) => setComment(e.target.value)}
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
    </>
  );
};

export default AddReviewModal;
