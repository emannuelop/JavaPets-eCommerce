import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit{
   
  images = [
    {
      src: 'https://coinculture.com/au/wp-content/uploads/2022/06/DOGECOIN-VS-BITCOIN-FEATURE-1150x600-1.png',
      alt: 'Imagem 1',
      link: 'https://www.example.com/image1'
    },
    {
      src: 'https://foxvet.com.br/wp-content/uploads/2020/06/banho-pet-1200x675.jpg',
      alt: 'Imagem 2',
      link: 'https://www.example.com/image2'
    },
    {
      src: 'https://www.amoviralata.com/wp-content/uploads/2020/09/viajar-com-pets.jpg',
      alt: 'Imagem 3',
      link: 'https://www.example.com/image3'
    },
    {
      src: "https://cdn.totallythebomb.com/wp-content/uploads/2021/04/Dog-Haircut-Feature-700x366.png",
      alt: 'Cão calvo',
      link:'https://www.example.com/image4'
    }
  ];

  currentImageIndex = 0;

  ngOnInit(): void {

    // Iniciar um temporizador para trocar de imagem a cada 5 segundos
    setInterval(() => {
      this.currentImageIndex = (this.currentImageIndex + 1) % this.images.length;
    }, 5000);
  
  }

  onImageClick(imageIndex: number) {
    this.currentImageIndex = imageIndex;
  }

}
