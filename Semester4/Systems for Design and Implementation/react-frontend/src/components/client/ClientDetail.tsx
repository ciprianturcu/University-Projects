import { Card, CardActions, CardContent, Container, IconButton } from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { Client } from "../../models/Client";
import { BACKEND_API_URL } from "../../constants";

export const ClientDetails = () => {
    const {clientId} = useParams();
    const [client, setClient] = useState<Client>();

    useEffect(() => {
        const fetchClient =async () => {
            const response = await fetch(`${BACKEND_API_URL}/client/${clientId}/`);
            const client = await response.json();
            setClient(client);
            console.log(client);
        };
        fetchClient();
    }, [clientId]);

    return (
        <Container>
        <Card>
            <CardContent>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/client`}>
                    <ArrowBackIcon />
                </IconButton>{" "}
                <h1 style={{textAlign:"center"}}>Client Details</h1>
                <p style={{textAlign:"left"}}>Name: {client?.name}</p>
                <p style={{textAlign:"left"}}>Phone Number: {client?.phone_number}</p>
                <p style={{textAlign:"left"}}>City: {client?.city}</p>
                <p style={{textAlign:"left"}}>Date of birth: {client?.date_of_birth}</p>
                <p style={{textAlign:"left"}}>Type: {client?.type}</p>
                <p style={{textAlign:"left"}}>Lawsuits:</p>
                <ul>
                    {client?.lawsuits?.map((lawsuit) => (
                        <li style={{textAlign:"left"}} key={lawsuit.id}>{lawsuit.description}</li>
                    ))}
                </ul>
            </CardContent>
            <CardActions>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/client/${client?.id}/edit`}>
                    <EditIcon />
                </IconButton>

                <IconButton component={Link} sx={{ mr: 3 }} to={`/client/${client?.id}/delete`}>
                    <DeleteForeverIcon sx={{ color: "red" }} />
                </IconButton>
            </CardActions>
        </Card>
    </Container>
    );
};