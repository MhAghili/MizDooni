import React, { useEffect, useState } from "react";
import "../classes.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Footer from "../components/Footer";
import { Header } from "../components/Header";
import { Modal, Button, Form } from "react-bootstrap";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const Customer = () => {
  const userName = localStorage.getItem("username");
  const [reservations, setReservations] = useState([]);
  const [user, setUser] = useState("");
  const [reservationToCancel, setReservationToCancel] = useState(null);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [isCancelChecked, setIsCancelChecked] = useState(false);
  const handleCancelClick = (reservation) => {
    setReservationToCancel(reservation);
    setShowCancelModal(true);
  };
  const handleCancelConfirmation = async () => {
    try {
      const reqBody = {
        username: reservationToCancel.user.username,
        reservationNumber: reservationToCancel.reservationNumber,
      };
      const response = await fetch("http://127.0.0.1:8080/DeleteReservation", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(reqBody),
      });
      if (response.ok) {
        toast.success("Reservation canceled successfully");

        fetchData();
      } else {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }
    } catch (error) {
      toast.error(error);
    }
    setShowCancelModal(false);
    setReservationToCancel(null);
  };
  async function fetchData() {
    try {
      const reservationsRes = await fetch(
        `http://127.0.0.1:8080/reservations/username=${userName}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      ).then((res) => res.json());
      setReservations(reservationsRes);
      const userRes = await fetch(
        `http://127.0.0.1:8080/users/${userName}`,{
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      ).then((res) => res.json());
      setUser(userRes);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  }
  useEffect(() => {
    fetchData();
    console.log("data fetched!");
  }, []);

  const isPastReservation = (dateTimeString) => {
    const reservationDate = new Date(dateTimeString);
    return reservationDate < new Date();
  };
  const extractHour = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    return `${hour}: 00`;
  };

  return (
    <>
      <Header name="My Reserves" />
      <div className="container py-5">
        <div className="row">
          <div className="col">
            <span>Your reservations are also emailed to </span>
            <a className="text-decoration-none">
              <span className="text-danger">{user.email}</span>
            </a>
          </div>

          <div className="col text-end">
            Address: {user.address?.city},{user.address?.country}
          </div>
        </div>

        <div className="row mt-3">
          <table className="table">
            <div className="py-3 rounded-top">
              {reservations.length !== 0 ? "My Reservations" : ""}
            </div>
            <tbody>
              {reservations.length !== 0 ? (
                reservations.map((reservation) => (
                  <tr
                    key={reservation.reservationNumber}
                    className={
                      isPastReservation(reservation.datetime)
                        ? "text-decoration-line-through"
                        : ""
                    }
                  >
                    <th scope="row" className="text-muted">
                      {extractHour(reservation.datetime)}
                    </th>
                    <td className="text-danger">
                      {reservation.restaurant.name}
                    </td>
                    <td className="text-muted">
                      Table-{reservation.tableNumber}
                    </td>
                    <td className="text-muted">
                      {reservation.numberOfPeople}-seats
                    </td>
                    <td className="text-center">
                      <a
                        onClick={() => handleCancelClick(reservation)}
                        className=" text-danger "
                        style={{ cursor: "pointer" }}
                      >
                        Cancle
                      </a>
                    </td>
                  </tr>
                ))
              ) : (
                <span className="text-center text-danger fs-4 pt-2 ms-3">
                  No Reservation
                </span>
              )}
            </tbody>
          </table>
        </div>
      </div>
      <Footer />
      {/* Cancel Reservation Modal */}
      <Modal show={showCancelModal} onHide={() => setShowCancelModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Cancel Reservation</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>Are you sure?</p>
          <Form.Check
            type="checkbox"
            label="Yes, I want to cancel this reservation."
            checked={isCancelChecked}
            onChange={(e) => setIsCancelChecked(e.target.checked)}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowCancelModal(false)}>
            Close
          </Button>
          <Button
            variant="danger"
            onClick={handleCancelConfirmation}
            disabled={!isCancelChecked}
          >
            Cancel Reservation
          </Button>
        </Modal.Footer>
      </Modal>
      <ToastContainer />
    </>
  );
};
