import { Card, CardActions, CardContent, Container, IconButton } from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { BACKEND_API_URL } from "../../constants";
import { Lawsuit } from "../../models/Lawsuit";

export const LawsuitDetails = () => {
    const {lawsuitId} = useParams();
    const [lawsuit, setLawsuit] = useState<Lawsuit>();

    useEffect(() => {
        const fetchLawsuit =async () => {
            const response = await fetch(`${BACKEND_API_URL}/lawsuit/${lawsuitId}/`);
            const lawsuit = await response.json();
            setLawsuit(lawsuit)
            console.log(lawsuit);
        };
        fetchLawsuit();
    }, [lawsuitId]);

    return (
        <Container>
        <Card>
            <CardContent>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/lawsuit`}>
                    <ArrowBackIcon />
                </IconButton>{" "}
                <h1 style={{textAlign:"center"}}>Lawsuit Details</h1>
                <p style={{textAlign:"left"}}>Description: {lawsuit?.description}</p>
                <p style={{textAlign:"left"}}>Type: {lawsuit?.type}</p>
                <p style={{textAlign:"left"}}>State: {lawsuit?.state}</p>
                <p style={{textAlign:"left"}}>Court Date: {lawsuit?.court_date}</p>
                <p style={{textAlign:"left"}}>Client: {lawsuit?.client?.name}</p>
            </CardContent>
            <CardActions>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/lawsuit/${lawsuit?.id}/edit`}>
                    <EditIcon />
                </IconButton>

                <IconButton component={Link} sx={{ mr: 3 }} to={`/lawsuit/${lawsuit?.id}/delete`}>
                    <DeleteForeverIcon sx={{ color: "red" }} />
                </IconButton>
            </CardActions>
        </Card>
    </Container>
    );
};