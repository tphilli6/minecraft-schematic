clc
clear all
close all

data=importdata('test.txt');
lz=data(1);%w 
ly=data(2);%h
lx=data(3);%l

%xzy
D=data(4:end);
Mat=reshape(D,[lx,lz,ly]);

xx=1:lx;
yy=1:ly;
zz=1:lz;
[x,y,z]=meshgrid(xx,zz,yy);

I=find(Mat>0);

%combines types of blocks for easier plotting and visualization with
%limited matlab symbol colors
I=find(D==3); D(I)=2;
I=find(D==4); D(I)=1;
I=find(D==17); D(I)=5;
I=find(D==43 | D==44); D(I)=1;
I=find(D==50); D(I)=0;
I=find(D==193); D(I)=0;

bids=unique(D);
c='krgb';

for i=2:numel(bids)
    I=find(D==bids(i));
    plot3(x(I),y(I),z(I),[c(i-1),'s'],'MarkerFaceColor',c(i-1),'MarkerSize',5)
    hold on
end
% axis equal
%         end
%     end
% endaxis equal

% lx=32;
% ly=32;
% lz=32;
% 
% D=zeros(lx,lz,ly);
% D(8:24,8:24,8:24)=1;
% Dvec=reshape(D,[lx*ly*lz,1]);
% fid=fopen('byte_input','w')
% fprintf(fid,'%4.0f\n',[lz;ly;lx;Dvec]);
% fclose(fid);

