import { Card, CardActions, CardContent, Container, IconButton } from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { Attorney } from "../../models/Attorney";
import { BACKEND_API_URL } from "../../constants";

export const AttorneyDetails = () => {
    const {attorneyId} = useParams();
    const [attorney, setAttorney] = useState<Attorney>();

    useEffect(() => {
        const fetchAttorney =async () => {
            const response = await fetch(`${BACKEND_API_URL}/attorney/${attorneyId}/`);
            const attorney = await response.json();
            setAttorney(attorney);
            console.log(attorney);
        };
        fetchAttorney();
    }, [attorneyId]);

    return (
        <Container>
        <Card>
            <CardContent>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/attorney`}>
                    <ArrowBackIcon />
                </IconButton>{" "}
                <h1 style={{textAlign:"center"}}>Attorney Details</h1>
                <p style={{textAlign:"left"}}>Name: {attorney?.name}</p>
                <p style={{textAlign:"left"}}>Specialization: {attorney?.specialization}</p>
                <p style={{textAlign:"left"}}>Birthday: {attorney?.date_of_birth}</p>
                <p style={{textAlign:"left"}}>Experience: {attorney?.experience}</p>
                <p style={{textAlign:"left"}}>City: {attorney?.city}</p>
                <p style={{textAlign:"left"}}>Fee: {attorney?.fee}</p>
                
            </CardContent>
            <CardActions>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/attorney/${attorney?.id}/edit`}>
                    <EditIcon />
                </IconButton>

                <IconButton component={Link} sx={{ mr: 3 }} to={`/attorney/${attorney?.id}/delete`}>
                    <DeleteForeverIcon sx={{ color: "red" }} />
                </IconButton>
            </CardActions>
        </Card>
    </Container>
    );
};